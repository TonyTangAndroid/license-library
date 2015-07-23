

package de.psdev.licensesdialog.licenses;

import android.content.Context;

import de.psdev.licensesdialog.R;

public class FontAwesomeLicense extends License {

    private static final long serialVersionUID = -5305394619884057474L;

    @Override
    public String getName() {
        return "Font Awesome License";
    }

    @Override
    public String readSummaryTextFromResources(final Context context) {
        return getContent(context, R.raw.font_awesome_summary);
    }

    @Override
    public String readFullTextFromResources(final Context context) {
        return getContent(context, R.raw.font_awesome_summary);
    }

    @Override
    public String getVersion() {
        return "";
    }

    @Override
    public String getUrl() {
        return "http://fortawesome.github.io/Font-Awesome/license/";
    }

}