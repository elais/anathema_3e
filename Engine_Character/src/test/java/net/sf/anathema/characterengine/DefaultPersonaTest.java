package net.sf.anathema.characterengine;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DefaultPersonaTest {

  private Qualities qualities = mock(Qualities.class);
  private Persona persona = new DefaultPersona(qualities);

  @Test
  public void callsCommandsWithQualities() throws Exception {
    Command command = mock(Command.class);
    persona.execute(command);
    verify(command).execute(qualities);
  }

  @Test
  public void executesQueriesOnQualities() throws Exception {
    QualityClosure closure = mock(QualityClosure.class);
    Type type = new Type("type");
    Name name = new Name("name");
    persona.doFor(new QualityKey(type, name), closure);
    verify(qualities).doFor(new QualityKey(type, name), closure);
  }
}