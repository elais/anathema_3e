package net.sf.anathema.hero.sheet.text;

import net.sf.anathema.hero.environment.report.Report;
import net.sf.anathema.hero.environment.report.ReportFactory;
import net.sf.anathema.library.initialization.Weight;
import net.sf.anathema.platform.environment.Environment;
import net.sf.anathema.platform.frame.ApplicationModel;
import net.sf.anathema.platform.report.RegisteredReportFactory;

@RegisteredReportFactory
@Weight(weight = 50)
public class TextReportFactory implements ReportFactory {

  @Override
  public Report[] createReport(Environment environment, ApplicationModel model) {
    return new Report[]{new TextReport(environment)};
  }
}
