DESCRIPTION = "Native ldconfig"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/files/COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"
PR = "r6"

do_configure() {
	echo "Done"
}

do_compile() {
	echo "Done"
}

# Only for XM/XM-ng at the moment!
# Copy ldconfig from cross-compiler to system
do_install() {
 	install -d 0755 ${D}${sbindir}
	install -d 0755 ${D}${sysconfdir}/ld.so.conf.d
	install -m 0755 ${EXTERNAL_TOOLCHAIN}/${CSL_TARGET_SYS_i686}/libc/usr/lib/bin/ldconfig ${D}${sbindir}
	install -m 0644 ${THISDIR}/files/ld.so.conf ${D}${sysconfdir}
	install -m 0755 ${EXTERNAL_TOOLCHAIN}/${CSL_TARGET_SYS_i686}/libc/usr/lib/bin/ldd ${D}${sbindir}
}