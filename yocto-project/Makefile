export BASEDIR=$(PWD)
YOCTOURL=git://git.yoctoproject.org
SOURCERYURL=git://github.com/MentorEmbedded

POKY := poky/.downloaded
META-INTEL := meta-intel/.downloaded
META-FSL-ARM := meta-fsl-arm/.downloaded
META-SOURCERY := meta-sourcery/.downloaded

API_INTERNAL_INCLUDEFILES=EepromConfig.h

# For SDK - works as long as all the systems use same QT-version
QT_VERSION=4.8.4

all: qemux86

# download metadata (arguments: name, branch, url)
download = \
	@echo "Downloading $(1)"; \
	rm -rf $(1); \
	git clone -b $(2) $(3)/$(1); \
	(cd $(1) && git reset --hard `cat ../$(1).hash`);

$(POKY): poky.hash
	$(call download,poky,dora,$(YOCTOURL))
# Need to remove old file from poky since meta-sourcery will provide new one
	@rm -f poky/meta/conf/distro/include/tcmode-external-sourcery.inc
# Comment out few lines, so image-live is generated without initrd;
# not pretty approach, so please do not take example from this
	@sed -i '/INITRD_IMAGE*/ s/^/#/' poky/meta/classes/image-live.bbclass
	@sed -i '/^SYSLINUX_ROOT*/ s/^/#/' poky/meta/classes/image-live.bbclass
	@touch $@

$(META-INTEL): meta-intel.hash
	$(call download,meta-intel,dora,$(YOCTOURL))
	@touch $@

$(META-FSL-ARM): meta-fsl-arm.hash
	$(call download,meta-fsl-arm,dora,$(YOCTOURL))
	@touch $@

$(META-SOURCERY): meta-sourcery.hash
	$(call download,meta-sourcery,master,$(SOURCERYURL))
# Small fixes needed to prevent installing empty dirs
	@sed -i '/cp -a $$sysroot\/sbin*/ s/cp/#cp/' meta-sourcery/recipes/meta/external-sourcery-toolchain.bb
	@sed -i '/for usr_element/ s/libexec sbin/libexec/' meta-sourcery/recipes/meta/external-sourcery-toolchain.bb
	@touch $@

# Some platforms use these and some don't. Be default these are commented out when downloading poky
# so uncomment them for those platforms that use them
fix-image-live:
	@sed -i '/^#INITRD_IMAGE*/ s/^#//' poky/meta/classes/image-live.bbclass
	@sed -i '/^#SYSLINUX_ROOT*/ s/^#//' poky/meta/classes/image-live.bbclass
	@touch $@

init: $(POKY) $(META-INTEL) $(META-FSL-ARM) $(META-SOURCERY)

qemux86: init
	make -C platform/$@

qemux86-sdk:  qemux86 clean_devel
	make -C platform/qemux86 sdk
	QT_VERSION=4.8.4 \
	DEVEL_DEST=$(PWD)/platform/qemux86/devel \
	PLATFORM_DIR=$(PWD)/platform/qemux86 \
	STAGING_DIR=$(PWD)/platform/qemux86/build/tmp/sysroots/qemux86 \
	QT_BUILD_DIR=$(PWD)/platform/qemux86/build/tmp/work/core2-poky-linux/qt4-x11-free/$(QT_VERSION)-r50.3 \
	TARGETLIBC=/opt/codesourcery/i686-pc-linux-gnu/libc \
	make general-sdk

general-sdk:
	@echo "Creating development folders"	
	@echo "In order this to work, the yocto-sstate - directory as well as build-directory"
	@echo "needs to be deleted before running make"
	@mkdir -p $(DEVEL_DEST)/{include,lib}	
	@mkdir -p $(DEVEL_DEST)/usr/{include,lib,bin}

# copy in bins, includes and libs from staging
	@cp -rad $(STAGING_DIR)/lib/*         $(DEVEL_DEST)/lib/
	@cp -rad $(STAGING_DIR)/usr/include/* $(DEVEL_DEST)/usr/include/
	@cp -rad $(STAGING_DIR)/usr/lib/*     $(DEVEL_DEST)/usr/lib/

# Copy QT-stuff only if QT_BUILD_DIR is provided
ifdef QT_BUILD_DIR
# Qt-stuff
	@mkdir -p $(DEVEL_DEST)/qt-$(QT_VERSION)
	@cp -rad $(QT_BUILD_DIR)/qt-everywhere-opensource-src-$(QT_VERSION)/bin  $(DEVEL_DEST)/qt-$(QT_VERSION)
	@cp -rad $(QT_BUILD_DIR)/qt-everywhere-opensource-src-$(QT_VERSION)/mkspecs  $(DEVEL_DEST)/qt-$(QT_VERSION)
	@cp -rad $(QT_BUILD_DIR)/qt-everywhere-opensource-src-$(QT_VERSION)/plugins  $(DEVEL_DEST)/qt-$(QT_VERSION)
	@cp -rad $(QT_BUILD_DIR)/qt-everywhere-opensource-src-$(QT_VERSION)/translations  $(DEVEL_DEST)/qt-$(QT_VERSION)

# FIXME!
	@cd $(DEVEL_DEST)/qt-$(QT_VERSION)/mkspecs && ln -s linux-g++ default
	@rm $(DEVEL_DEST)/qt-$(QT_VERSION)/bin/qmake
	@cd $(DEVEL_DEST)/qt-$(QT_VERSION)/bin && ln -s qmake2 qmake
	@cp $(PLATFORM_DIR)/qmake.conf $(DEVEL_DEST)/qt-$(QT_VERSION)/mkspecs/default
	@cp $(PLATFORM_DIR)/qt.conf $(DEVEL_DEST)/qt-$(QT_VERSION)/bin
endif

# remove some
	@rm -rf $(DEVEL_DEST)/usr/lib/{gconv,locale,bin,*.a,*.la,*.map,*~,terminfo}

# remove those that are provided by compiler package
	@cd $(DEVEL_DEST) && find $(TARGETLIBC)/usr/lib/      -xtype f  |sed -r "s,^.*/libc/,," | xargs rm -f
	@cd $(DEVEL_DEST) && find usr/include -empty  | xargs rm -rf 
	@cd $(DEVEL_DEST) && find $(TARGETLIBC)/lib/          -xtype f  |sed -r "s,^.*/libc/,," | xargs rm -f

# remove internal api headers (preferably they shouldn't be installed in the first place)
	@cd $(DEVEL_DEST)/usr/include && rm -rf $(API_INTERNAL_INCLUDEFILES)

clean_devel:
	@rm -rf $(DEVEL_DEST)

.PHONY: all init qemux86 qemux86-sdk fix-image-live
