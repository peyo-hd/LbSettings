package com.peyo.lbsettings;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;

public class XmlReader {
    private final Context mContext;
    private final int mXmlResource;
    private final String mRootNodeName;
    private final String mNodeNameRequested;
    private final XmlReaderListener mListener;
    
    public interface XmlReaderListener {
        void handleRequestedNode(Context context, XmlResourceParser parser, AttributeSet attrs)
                throws org.xmlpull.v1.XmlPullParserException, IOException;
    }

    XmlReader(Context context, int xmlResource, String rootNodeName, String nodeNameRequested,
            XmlReaderListener listener) {
        mContext = context;
        mXmlResource = xmlResource;
        mRootNodeName = rootNodeName;
        mNodeNameRequested = nodeNameRequested;
        mListener = listener;
    }

    void read() {
        XmlResourceParser parser = null;
        try {
            parser = mContext.getResources().getXml(mXmlResource);
            AttributeSet attrs = Xml.asAttributeSet(parser);

            int type;
            while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
                    && type != XmlPullParser.START_TAG) {
                // Parse next until start tag is found
            }

            String nodeName = parser.getName();
            if (!mRootNodeName.equals(nodeName)) {
                throw new RuntimeException("XML document must start with <" + mRootNodeName
                        + "> tag; found" + nodeName + " at " + parser.getPositionDescription());
            }

            final int outerDepth = parser.getDepth();
            while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
                    && (type != XmlPullParser.END_TAG || parser.getDepth() > outerDepth)) {
                if (type == XmlPullParser.END_TAG || type == XmlPullParser.TEXT) {
                    continue;
                }

                nodeName = parser.getName();
                if (mNodeNameRequested.equals(nodeName)) {
                    mListener.handleRequestedNode(mContext, parser, attrs);
                } else {
                    XmlUtils.skipCurrentTag(parser);
                }
            }

        } catch (XmlPullParserException|IOException e) {
            throw new RuntimeException("Error parsing headers", e);
        } finally {
            if (parser != null)
                parser.close();
        }
    }


}
