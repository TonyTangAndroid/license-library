

package de.psdev.licensesdialog;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

public final class NoticesHtmlBuilder {

    private final Context mContext;
    private final Map<License, String> mLicenseTextCache = new HashMap<License, String>();
    private Notices mNotices;
    private Notice mNotice;
    private String mStyle;
    private boolean mShowFullLicenseText;

    public static NoticesHtmlBuilder create(final Context context) {
        return new NoticesHtmlBuilder(context);
    }

    private NoticesHtmlBuilder(final Context context) {
        mContext = context;
        mStyle = context.getResources().getString(R.string.notices_default_style);
        mShowFullLicenseText = false;
    }

    public NoticesHtmlBuilder setNotices(final Notices notices) {
        mNotices = notices;
        mNotice = null;
        return this;
    }

    public NoticesHtmlBuilder setNotice(final Notice notice) {
        mNotice = notice;
        mNotices = null;
        return this;
    }

    public NoticesHtmlBuilder setStyle(final String style) {
        mStyle = style;
        return this;
    }

    public NoticesHtmlBuilder setShowFullLicenseText(final boolean showFullLicenseText) {
        mShowFullLicenseText = showFullLicenseText;
        return this;
    }

    public String build() {
        final StringBuilder noticesHtmlBuilder = new StringBuilder(500);
        appendNoticesContainerStart(noticesHtmlBuilder);
        if (mNotice != null) {
            appendNoticeBlock(noticesHtmlBuilder, mNotice);
        } else if (mNotices != null) {
            for (final Notice notice : mNotices.getNotices()) {
                appendNoticeBlock(noticesHtmlBuilder, notice);
            }
        } else {
            throw new IllegalStateException("no notice(s) set");
        }
        appendNoticesContainerEnd(noticesHtmlBuilder);
        return noticesHtmlBuilder.toString();
    }

    //

    private void appendNoticesContainerStart(final StringBuilder noticesHtmlBuilder) {
        noticesHtmlBuilder.append("<!DOCTYPE html><html><head>")
            .append("<style type=\"text/css\">").append(mStyle).append("</style>")
            .append("</head><body>");
    }

    private void appendNoticeBlock(final StringBuilder noticesHtmlBuilder, final Notice notice) {
        noticesHtmlBuilder.append("<ul><li>").append(notice.getName());
        final String currentNoticeUrl = notice.getUrl();
        if (currentNoticeUrl != null && currentNoticeUrl.length() > 0) {
            noticesHtmlBuilder.append(" (<a href=\"").append(currentNoticeUrl).append("\">").append(currentNoticeUrl).append("</a>)");
        }
        noticesHtmlBuilder.append("</li></ul>");
        noticesHtmlBuilder.append("<pre>");
        final String copyright = notice.getCopyright();
        if (copyright != null) {
            noticesHtmlBuilder.append(copyright).append("<br/><br/>");
        }
        noticesHtmlBuilder.append(getLicenseText(notice.getLicense())).append("</pre>");
    }

    private void appendNoticesContainerEnd(final StringBuilder noticesHtmlBuilder) {
        noticesHtmlBuilder.append("</body></html>");
    }

    private String getLicenseText(final License license) {
        if (license != null) {
            if (!mLicenseTextCache.containsKey(license)) {
                mLicenseTextCache.put(license, mShowFullLicenseText ? license.getFullText(mContext) : license.getSummaryText(mContext));
            }
            return mLicenseTextCache.get(license);
        }
        return "";
    }
}
