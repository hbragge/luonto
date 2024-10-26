DESCRIPTION = "embedded image"
LICENSE = "MIT"

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

inherit core-image

# Generic Yocto SW
#IMAGE_INSTALL += "icu qtperf4 openssh-sftp parted perl apt"

# Modules
IMAGE_INSTALL += "ss"

# Libraries
IMAGE_INSTALL += "appapi"

# Applications
IMAGE_INSTALL += "appauxd"

firmware_version() {
	echo "0.0.0.1" > ${IMAGE_ROOTFS}${sysconfdir}/firmware.ver
}

ROOTFS_POSTPROCESS_COMMAND += "firmware_version; "
