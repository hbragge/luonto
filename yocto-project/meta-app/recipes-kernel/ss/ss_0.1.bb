DESCRIPTION = "SS SPI communication module"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

inherit localmodule update-rc.d

PR = "r1"

export INSTALL_MOD_DIR="kernel/drivers/cc-extra"

EXTRA_OEMAKE = " KERNEL_DIR=${STAGING_KERNEL_DIR} "

PKG_${PN} = "kernel-module-${PN}"

do_install() {
	install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/cc-extra/
	install -m 0644 ss.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/cc-extra

	install -d ${D}${sysconfdir}/init.d/
	install -m 0755 ${THISDIR}/files/ss-init ${D}${sysconfdir}/init.d/ss
}

FILES_kernel-module-${PN} += "${sysconfdir}/init.d/ss"

INITSCRIPT_PACKAGES="kernel-module-${PN}"
INITSCRIPT_NAME="ss"
INITSCRIPT_PARAMS="start 05 S . stop 95 0 1 6 ."
