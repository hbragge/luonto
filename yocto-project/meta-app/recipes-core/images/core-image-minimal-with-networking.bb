DESCRIPTION = "A small image just capable of allowing a device to boot."

IMAGE_FEATURES += "ssh-server-openssh package-management"

IMAGE_INSTALL = "packagegroup-core-boot ${ROOTFS_PKGMANAGE_BOOTSTRAP} ${CORE_IMAGE_EXTRA_INSTALL}"
IMAGE_INSTALL += "openssh-scp openssh-sftp nano coreutils bash nano iproute2"

# Graphical system
IMAGE_FEATURES += "x11-base qt4-pkgs"
IMAGE_INSTALL += "x11vnc"

IMAGE_LINGUAS = " "

LICENSE = "MIT"

# Total image size
IMAGE_ROOTFS_SIZE = "2242880"

inherit core-image
