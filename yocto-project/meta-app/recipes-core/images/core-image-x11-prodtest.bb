DESCRIPTION = "prodtest image"
LICENSE = "MIT"

IMAGE_FEATURES += "splash package-management x11-base ssh-server-openssh"

SYSLINUX_ROOT = "root=/dev/sda2 rootwait "
ROOTFS = "${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}-${MACHINE}.ext4"
do_bootdirectdisk[depends] += "${PN}:do_rootfs"
# Generate bootimg after directdisk, so that rootfs will not be included in bootimg
do_bootimg[depends] += "${PN}:do_bootdirectdisk "

inherit core-image boot-directdisk

# Generic Yocto SW
IMAGE_INSTALL += "icu parted e2fsprogs e2fsprogs-tune2fs perl apt bash nano lsb coreutils net-tools tzdata"
IMAGE_INSTALL += "openssh-sftp openssh-sftp-server openssh-scp"
IMAGE_INSTALL += "wpa-supplicant"

# Modules
IMAGE_INSTALL += "lpc-fpga i2c-comm socket-can"

# Libraries
IMAGE_INSTALL += "appapi"

# Applications
IMAGE_INSTALL += "appsettingsconsole snb users"

preprocess() {
	echo "0.0.0.1" > ${IMAGE_ROOTFS}${sysconfdir}/firmware.ver

	mkdir -p ${IMAGE_ROOTFS}${sysconfdir}/network
	rm -f ${IMAGE_ROOTFS}${sysconfdir}/network/interfaces
	ln -s /opt/etc/interfaces ${IMAGE_ROOTFS}${sysconfdir}/network/interfaces
	rm -rf ${IMAGE_ROOTFS}${sysconfdir}/ssh
	ln -s /opt/etc/ssh ${IMAGE_ROOTFS}${sysconfdir}/ssh
	rm -f ${IMAGE_ROOTFS}${sysconfdir}/hostname
	ln -s /opt/etc/hostname ${IMAGE_ROOTFS}${sysconfdir}/hostname
	rm -f ${IMAGE_ROOTFS}${sysconfdir}/localtime
	ln -s /opt/etc/localtime ${IMAGE_ROOTFS}${sysconfdir}/localtime
	rm -f ${IMAGE_ROOTFS}${sysconfdir}/passwd
	ln -s /opt/etc/passwd ${IMAGE_ROOTFS}${sysconfdir}/passwd
	rm -f ${IMAGE_ROOTFS}${sysconfdir}/shadow
	ln -s /opt/etc/shadow ${IMAGE_ROOTFS}${sysconfdir}/shadow
}

IMAGE_PREPROCESS_COMMAND += "preprocess; "

copy_syslinuxcfg() {
	cp ${THISDIR}/files/syslinux.cfg.serialconsole ${S}/syslinux.cfg
}

python build_syslinux_cfg () {
    bb.build.exec_func('copy_syslinuxcfg', d)
}
