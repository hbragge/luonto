DESCRIPTION = "recovery image"
LICENSE = "MIT"

# NOTE: x11-base not required for rescue system, but it's included
# so that boot does not hang (because of some bad init script?)
IMAGE_FEATURES += "splash package-management x11-base ssh-server-openssh"

ROOTFS = "${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}-${MACHINE}.ext4"

inherit core-image

# Generic Yocto SW
IMAGE_INSTALL += "icu openssh-sftp parted e2fsprogs e2fsprogs-tune2fs"
IMAGE_INSTALL += "ifplugd dhcp-client"

# Modules
IMAGE_INSTALL += " lpc-fpga i2c-comm"

# Libraries
IMAGE_INSTALL += "appapi"

# Applications
IMAGE_INSTALL += "snb appsettingsconsole appauxd users"

preprocess() {
	echo "0.0.0.1" > ${IMAGE_ROOTFS}${sysconfdir}/firmware.ver

# create-opt.sh installed from initscripts package, need to remove it
	rm -f ${IMAGE_ROOTFS}${sysconfdir}/init.d/create-opt.sh
	rm -f ${IMAGE_ROOTFS}${sysconfdir}/rc*.d/S*create-opt.sh

	cp -f ${IMAGE_ROOTFS}${sysconfdir}/hostname.orig ${IMAGE_ROOTFS}${sysconfdir}/hostname
	cp -f ${IMAGE_ROOTFS}${sysconfdir}/passwd.orig ${IMAGE_ROOTFS}${sysconfdir}/passwd
	cp -f ${IMAGE_ROOTFS}${sysconfdir}/shadow.orig ${IMAGE_ROOTFS}${sysconfdir}/shadow

	rm -rf ${IMAGE_ROOTFS}${sysconfdir}/dhcp/dhclient.conf
	cp -f ${IMAGE_ROOTFS}${sysconfdir}/dhcp/dhclient.conf.orig ${IMAGE_ROOTFS}${sysconfdir}/dhcp/dhclient.conf
}

IMAGE_PREPROCESS_COMMAND += "preprocess; "
