
package de.psdev.licensesdialog.licenses;

import android.content.Context;
import de.psdev.psdev.licensesdialog.R;

public class ApacheSoftwareLicense20 extends License {

    private static final long serialVersionUID = 4854000061990891449L;

    @Override
    public String getName() {
        return "Apache Software License 2.0";
    }

    @Override
    public String readSummaryTextFromResources(final Context context) {
        return getContent(context, R.raw.asl_20_summary);
    }

    @Override
    public String readFullTextFromResources(final Context context) {
        return getContent(context, R.raw.asl_20_summary);
    }

    @Override
    public String getVersion() {
        return "2.0";
    }

    @Override
    public String getUrl() {
        return "http://www.apache.org/licenses/LICENSE-2.0.txt";
    }


}
