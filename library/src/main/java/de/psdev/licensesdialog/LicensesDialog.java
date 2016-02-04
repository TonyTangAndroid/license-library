

package de.psdev.licensesdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.webkit.WebView;

import java.util.List;

import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;
import de.psdev.psdev.licensesdialog.R;

public class LicensesDialog {
    public static final Notice LICENSES_DIALOG_NOTICE = new Notice("LicensesDialog", "http://psdev.de/LicensesDialog",
            "Copyright 2013 Philip Schiffer",
            new ApacheSoftwareLicense20());

    private final Context mContext;
    private final String mTitleText;
    private final String mLicensesText;
    private final String mCloseText;
    private final int mThemeResourceId;
    private final int mDividerColor;

    //
    private LicensesDialog(final Context context, final String licensesText, final String titleText, final String closeText, final int themeResourceId,
                           final int dividerColor) {
        mContext = context;
        mTitleText = titleText;
        mLicensesText = licensesText;
        mCloseText = closeText;
        mThemeResourceId = themeResourceId;
        mDividerColor = dividerColor;
    }

    public Dialog create() {
        //Get resources
        final WebView webView = new WebView(mContext);
        webView.loadDataWithBaseURL(null, mLicensesText, "text/html", "utf-8", null);
        final AlertDialog.Builder builder;
        if (mThemeResourceId != 0) {
            builder = new AlertDialog.Builder(new ContextThemeWrapper(mContext, mThemeResourceId));
        } else {
            builder = new AlertDialog.Builder(mContext);
        }
        builder.setTitle(mTitleText)
                .setView(webView)
                .setPositiveButton(mCloseText, new Dialog.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        dialogInterface.dismiss();
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                if (mDividerColor != 0) {
                    // Set title divider color
                    final int titleDividerId = mContext.getResources().getIdentifier("titleDivider", "id", "android");
                    final View titleDivider = dialog.findViewById(titleDividerId);
                    if (titleDivider != null) {
                        titleDivider.setBackgroundColor(mDividerColor);
                    }
                }
            }
        });
        return dialog;
    }

    public Dialog show() {
        final Dialog dialog = create();
        dialog.show();
        return dialog;
    }

    //

    private static Notices getNotices(final Context context, final int rawNoticesResourceId) {
        try {
            final Resources resources = context.getResources();
            if ("raw".equals(resources.getResourceTypeName(rawNoticesResourceId))) {
                return NoticesXmlParser.parse(resources.openRawResource(rawNoticesResourceId));
            } else {
                throw new IllegalStateException("not a raw resource");
            }
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static String getLicensesText(final Context context, final Notices notices, final boolean showFullLicenseText,
                                          final boolean includeOwnLicense, final String style) {
        try {
            if (includeOwnLicense) {
                final List<Notice> noticeList = notices.getNotices();
                noticeList.add(LICENSES_DIALOG_NOTICE);
            }
            return NoticesHtmlBuilder.create(context).setShowFullLicenseText(showFullLicenseText).setNotices(notices).setStyle(style).build();
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static Notices getSingleNoticeNotices(final Notice notice) {
        final Notices notices = new Notices();
        notices.addNotice(notice);
        return notices;
    }

    // Inner classes

    public static final class Builder {

        private final Context mContext;

        // Default values
        private String mTitleText;
        private String mCloseText;
        private Integer mRawNoticesId;
        private Notices mNotices;
        private String mNoticesText;
        private String mNoticesStyle;
        private boolean mShowFullLicenseText;
        private boolean mIncludeOwnLicense;
        private int mThemeResourceId;
        private int mDividerColor;

        public Builder(final Context context) {
            mContext = context;
            mTitleText = context.getString(R.string.notices_title);
            mCloseText = context.getString(R.string.notices_close);
            mNoticesStyle = context.getString(R.string.notices_default_style);
            mShowFullLicenseText = false;
            mIncludeOwnLicense = false;
            mThemeResourceId = 0;
            mDividerColor = 0;
        }

        public Builder setTitle(final int titleId) {
            mTitleText = mContext.getString(titleId);
            return this;
        }

        public Builder setTitle(final String title) {
            mTitleText = title;
            return this;
        }

        public Builder setCloseText(final String closeText) {
            mCloseText = closeText;
            return this;
        }

        public Builder setNotices(final int rawNoticesId) {
            mRawNoticesId = rawNoticesId;
            mNotices = null;
            return this;
        }

        public Builder setNotices(final Notices notices) {
            mNotices = notices;
            mRawNoticesId = null;
            return this;
        }

        public Builder setNotices(final Notice notice) {
            return setNotices(getSingleNoticeNotices(notice));
        }

        Builder setNotices(final String notices) {
            mNotices = null;
            mRawNoticesId = null;
            mNoticesText = notices;
            return this;
        }

        public Builder setIncludeOwnLicense(final boolean includeOwnLicense) {
            mIncludeOwnLicense = includeOwnLicense;
            return this;
        }

        public Builder setThemeResourceId(final int themeResourceId) {
            mThemeResourceId = themeResourceId;
            return this;
        }

        public Builder setDividerColor(final int dividerColor) {
            mDividerColor = dividerColor;
            return this;
        }

        public Builder setDividerColorId(final int dividerColorId) {
            mDividerColor = mContext.getResources().getColor(dividerColorId);
            return this;
        }

        public LicensesDialog build() {
            final String licensesText;
            if (mNotices != null) {
                licensesText = getLicensesText(mContext, mNotices, mShowFullLicenseText, mIncludeOwnLicense, mNoticesStyle);
            } else if (mRawNoticesId != null) {
                licensesText = getLicensesText(mContext, getNotices(mContext, mRawNoticesId), mShowFullLicenseText, mIncludeOwnLicense, mNoticesStyle);
            } else if (mNoticesText != null) {
                licensesText = mNoticesText;
            } else {
                throw new IllegalStateException("Notices have to be provided, see setNotices");
            }

            return new LicensesDialog(mContext, licensesText, mTitleText, mCloseText, mThemeResourceId, mDividerColor);
        }

    }
}