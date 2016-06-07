package com.peyo.lbsettings;

public class MenuItem {

    public interface UriGetter {
        String getUri();
    }

    public interface TextGetter {
        String getText();
    }

    public static class Builder {
        private int mId;
        private String mTitle;
        private TextGetter mDescriptionGetter;
        private int mIconRes;

        public Builder from(MenuItem item) {
            mId = item.mId;
            mTitle = item.mDisplayName;
            mDescriptionGetter = item.mDisplayDescriptionTextGetter;
            mIconRes = item.mIconRes;
            return this;
        }

        public Builder id(int id) {
            mId = id;
            return this;
        }

        public Builder title(String title) {
            mTitle = title;
            return this;
        }

        public Builder descriptionGetter(TextGetter descriptionGetter) {
            mDescriptionGetter = descriptionGetter;
            return this;
        }

        public Builder description(String description) {
            return descriptionGetter(new MenuItem.ConstantTextGetter(description));
        }

        public Builder imageResourceId(int imageResourceId) {
            mIconRes = imageResourceId;
            return this;
        }

        public MenuItem build() {
            return new MenuItem(mId, mTitle, mDescriptionGetter, mIconRes);
        }
    }

    private static class ConstantTextGetter implements MenuItem.TextGetter {

        private final String mText;

        public ConstantTextGetter(String text) {
            mText = text;
        }

        @Override
        public String getText() {
            return mText;
        }
    }

    private final int mId;
    private final String mDisplayName;
    private final TextGetter mDisplayDescriptionTextGetter;
    private final int mIconRes;

    private MenuItem(int id, String displayName,
            TextGetter displayDescriptionTextGetter, int iconRes) {
        mId = id;
        mDisplayName = displayName;
        mDisplayDescriptionTextGetter = displayDescriptionTextGetter;
        mIconRes = iconRes;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mDisplayName;
    }

    public TextGetter getDescriptionGetter() {
        return mDisplayDescriptionTextGetter;
    }

    public int getIconRes() {
        return mIconRes;
    }

}
