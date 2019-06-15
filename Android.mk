LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_PACKAGE_NAME := LbSettings
LOCAL_MODULE_TAGS := optional
LOCAL_USE_AAPT2 := true
LOCAL_PRIVATE_PLATFORM_APIS := true

LOCAL_STATIC_JAVA_LIBRARIES := \
    androidx.legacy_legacy-support-v4 \
    androidx.recyclerview_recyclerview \
    androidx.leanback_leanback \
    gson-2.8.5

LOCAL_RESOURCE_DIR := \
    $(TOP)/frameworks/support/leanback/src/main/res \
    $(TOP)/frameworks/support/v7/recyclerview/res \
    $(LOCAL_PATH)/res \

LOCAL_SRC_FILES := $(call all-java-files-under, src)

include $(BUILD_PACKAGE)

include $(CLEAR_VARS)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := gson-2.8.5:libs/gson-2.8.5.jar
include $(BUILD_MULTI_PREBUILT)
