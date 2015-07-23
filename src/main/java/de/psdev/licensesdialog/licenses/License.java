
package de.psdev.licensesdialog.licenses;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

public abstract class License implements Serializable {

    private static final long serialVersionUID = 3100331505738956523L;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private String mCachedSummaryText = null;
    private String mCachedFullText = null;

    public abstract String getName();

    public abstract String readSummaryTextFromResources(final Context context);

    public abstract String readFullTextFromResources(final Context context);

    public abstract String getVersion();

    public abstract String getUrl();

    //

    public final String getSummaryText(final Context context) {
        if (mCachedSummaryText == null) {
            mCachedSummaryText = readSummaryTextFromResources(context);
        }

        return mCachedSummaryText;
    }

    public final String getFullText(final Context context) {
        if (mCachedFullText == null) {
            mCachedFullText = readFullTextFromResources(context);
        }
        
        return mCachedFullText;
    }

    protected String getContent(final Context context, final int contentResourceId) {
        BufferedReader reader = null;
        try {
            final InputStream inputStream = context.getResources().openRawResource(contentResourceId);
            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));
                return toString(reader);
            }
            throw new IOException("Error opening license file.");
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    // Don't care.
                }
            }
        }
    }

    private String toString(final BufferedReader reader) throws IOException {
        final StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append(LINE_SEPARATOR);
        }
        return builder.toString();
    }

}
