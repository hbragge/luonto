DESCRIPTION = "basic image"
LICENSE = "MIT"

IMAGE_FEATURES += "splash package-management x11-base ssh-server-openssh"

SYSLINUX_ROOT = "root=/dev/sda2 rootwait "
ROOTFS = "${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}-${MACHINE}.ext4"
do_bootdirectdisk[depends] += "${PN}:do_rootfs recovery-image:do_rootfs "
# Generate bootimg after directdisk, so that rootfs will not be included in bootimg
do_bootimg[depends] += "${PN}:do_bootdirectdisk "

inherit core-image boot-directdisk

# Generic Yocto SW
IMAGE_INSTALL += "icu qtperf4 parted e2fsprogs e2fsprogs-tune2fs perl apt bash nano lsb coreutils net-tools tzdata"
IMAGE_INSTALL += "openssh-sftp openssh-sftp-server openssh-scp"
IMAGE_INSTALL += "wpa-supplicant x11vnc iproute2 dhcp-client dhcp-server dhcp-server-config ifplugd"
IMAGE_INSTALL += "suspend-utils ppp bluez4 qt4-x11-free"

# Modules
IMAGE_INSTALL += " lpc-fpga i2c-comm rtl8192su"

# Libraries
IMAGE_INSTALL += "appapi"

# Applications
IMAGE_INSTALL += "snb startuplauncher appsettingsconsole appsettings appauxd appvideo touchcalibrator"
IMAGE_INSTALL += "socket-can-utils ldconfig users tofrodos"

preprocess() {
	# Should this be platform depended instead of common?
	echo "2.0.0.0" > ${IMAGE_ROOTFS}${sysconfdir}/firmware.ver

	mkdir -p ${IMAGE_ROOTFS}${sysconfdir}/network
	rm -f ${IMAGE_ROOTFS}${sysconfdir}/network/interfaces
	ln -s /opt/etc/interfaces ${IMAGE_ROOTFS}${sysconfdir}/network/interfaces
	rm -rf ${IMAGE_ROOTFS}${sysconfdir}/ssh
	ln -s /opt/etc/ssh ${IMAGE_ROOTFS}${sysconfdir}/ssh
	rm -f ${IMAGE_ROOTFS}${sysconfdir}/hostname
	ln -s /opt/etc/hostname ${IMAGE_ROOTFS}${sysconfdir}/hostname
	rm -f ${IMAGE_ROOTFS}${sysconfdir}/localtime
	ln -s /opt/etc/localtime ${IMAGE_ROOTFS}${sysconfdir}/localtime
	rm -f ${IMAGE_ROOTFS}${sysconfdir}/timestamp
	ln -s /opt/etc/timestamp ${IMAGE_ROOTFS}${sysconfdir}/timestamp
	rm -f ${IMAGE_ROOTFS}${sysconfdir}/passwd
	ln -s /opt/etc/passwd ${IMAGE_ROOTFS}${sysconfdir}/passwd
	rm -f ${IMAGE_ROOTFS}${sysconfdir}/shadow
	ln -s /opt/etc/shadow ${IMAGE_ROOTFS}${sysconfdir}/shadow
	rm -rf ${IMAGE_ROOTFS}${sysconfdir}/ld.so.cache
	ln -s /opt/etc/ld.so.cache ${IMAGE_ROOTFS}${sysconfdir}/ld.so.cache
	rm -rf ${IMAGE_ROOTFS}${sysconfdir}/ld.so.conf.d
	ln -s /opt/etc/ld.so.conf.d ${IMAGE_ROOTFS}${sysconfdir}/ld.so.conf.d
	rm -rf ${IMAGE_ROOTFS}${sysconfdir}/dhcp/dhclient.conf
	ln -s /opt/etc/dhclient.conf ${IMAGE_ROOTFS}${sysconfdir}/dhcp/dhclient.conf
	rm -rf ${IMAGE_ROOTFS}${sysconfdir}/dhcp/dhcpd.conf
	ln -s /opt/etc/dhcpd.conf ${IMAGE_ROOTFS}${sysconfdir}/dhcp/dhcpd.conf
}

IMAGE_PREPROCESS_COMMAND += "preprocess; "

do_add_recovery_image() {
	IMAGE=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.hdddirect
	RECOVERY_ROOTFS=${DEPLOY_DIR_IMAGE}/recovery-image-${MACHINE}.ext4

	if [ ! -s "${IMAGE}" ]; then
		echo "hdddirect image required"
		exit 1
	fi

	RECOVERY_ROOTFSBLOCKS=`du -Lbks ${RECOVERY_ROOTFS} | cut -f 1`
	# Start the third partition after the end of the second
	START=`parted ${IMAGE} unit B print|grep '^ 2'|tr -s ' '|cut -d ' ' -f 4|sed 's/B$//'`
	START=`expr $START + 1`
	END=`expr \( $RECOVERY_ROOTFSBLOCKS \* 1024 \) + $START`
	ENDOFFSET=`expr $END / 1024`
	echo $START $END $ENDOFFSET
	# Expand image, so that new partition can be created
	dd if=/dev/zero of=$IMAGE bs=1024 seek=${ENDOFFSET} count=1
	parted ${IMAGE} unit B print
	parted $IMAGE unit B mkpart primary ext2 ${START}B ${END}B
	OFFSET=`expr $START / 512`
	dd if=${RECOVERY_ROOTFS} of=$IMAGE conv=notrunc seek=$OFFSET bs=512
}

# Recovery system disabled now, requires more testing
addtask add_recovery_image before do_build after do_bootimg

copy_syslinuxcfg() {
	cp ${THISDIR}/files/syslinux.cfg ${S}/syslinux.cfg
}

python build_syslinux_cfg () {
    bb.build.exec_func('copy_syslinuxcfg', d)
}
