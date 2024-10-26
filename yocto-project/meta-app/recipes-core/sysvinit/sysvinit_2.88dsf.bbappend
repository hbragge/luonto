FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR := "${PR}.1"

do_install_append() {
	#sed -i 's/ROOTFS_READ_ONLY=no/ROOTFS_READ_ONLY=yes/' ${D}${sysconfdir}/default/rcS
}
