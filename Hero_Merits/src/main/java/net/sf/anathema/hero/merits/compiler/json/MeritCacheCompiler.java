package net.sf.anathema.hero.merits.compiler.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.anathema.hero.environment.initialization.ExtensibleDataSet;
import net.sf.anathema.hero.environment.initialization.ExtensibleDataSetCompiler;
import net.sf.anathema.hero.environment.template.TemplateLoader;
import net.sf.anathema.hero.individual.persistence.GenericTemplateLoader;
import net.sf.anathema.hero.individual.persistence.RuntimeTypeAdapterFactory;
import net.sf.anathema.hero.merits.compiler.MeritCacheImpl;
import net.sf.anathema.hero.merits.compiler.json.template.MeritListTemplate;
import net.sf.anathema.hero.merits.compiler.json.template.requirements.MeritGroupRequirementsTemplate;
import net.sf.anathema.hero.merits.compiler.json.template.requirements.MeritMortalRequirementsTemplate;
import net.sf.anathema.hero.merits.compiler.json.template.requirements.MeritRequirementsTemplate;
import net.sf.anathema.hero.merits.compiler.json.template.requirements.MeritSupernaturalMeritsRequirementsTemplate;
import net.sf.anathema.hero.merits.compiler.json.template.requirements.MeritTraitRequirementsTemplate;
import net.sf.anathema.library.exception.PersistenceException;
import net.sf.anathema.library.initialization.ObjectFactory;
import net.sf.anathema.library.resources.ResourceFile;

@net.sf.anathema.platform.initialization.ExtensibleDataSetCompiler
public class MeritCacheCompiler implements ExtensibleDataSetCompiler {

	private static final String Merit_File_Recognition_Pattern = ".+?\\.merits";
	private final List<ResourceFile> resourceFiles = new ArrayList<>();
	private final TemplateLoader<MeritListTemplate> meritsLoader;
	private final ObjectFactory objectFactory;
	
	public MeritCacheCompiler(ObjectFactory objectFactory) {
	    this.objectFactory = objectFactory;
	    
	    // TODO: There should be a reflections based means to compile this
	    final RuntimeTypeAdapterFactory<MeritRequirementsTemplate> typeFactory = RuntimeTypeAdapterFactory
                .of(MeritRequirementsTemplate.class, MeritRequirementsTemplate.jsonLabel)
                .registerSubtype(MeritGroupRequirementsTemplate.class, MeritGroupRequirementsTemplate.jsonLabel)
                .registerSubtype(MeritTraitRequirementsTemplate.class, MeritTraitRequirementsTemplate.jsonLabel)
                .registerSubtype(MeritMortalRequirementsTemplate.class, MeritMortalRequirementsTemplate.jsonLabel)
                .registerSubtype(MeritSupernaturalMeritsRequirementsTemplate.class, MeritSupernaturalMeritsRequirementsTemplate.jsonLabel);
	    
	    meritsLoader = new GenericTemplateLoader<>(MeritListTemplate.class, typeFactory);
	  }
	
	@Override
	public String getName() {
		return "Merits";
	}

	@Override
	public String getRecognitionPattern() {
		return Merit_File_Recognition_Pattern;
	}

	@Override
	public void registerFile(ResourceFile resource) {
		resourceFiles.add(resource);
	}

	@Override
	public ExtensibleDataSet build() {
		MeritCacheBuilder meritsBuilder = new MeritCacheBuilder();
	    resourceFiles.forEach(resourceFile -> {
	      meritsBuilder.addTemplate(loadTemplate(resourceFile, meritsLoader));
	    });
	    MeritCacheImpl meritCache = meritsBuilder.createCache();
	    return meritCache;
	}
	
	private <T> T loadTemplate(ResourceFile resource, TemplateLoader<T> loader) {
	    try (InputStream inputStream = resource.getURL().openStream()) {
	      return loader.load(inputStream);
	    } catch (IOException e) {
	      throw new PersistenceException(e);
	    }
	  }

}