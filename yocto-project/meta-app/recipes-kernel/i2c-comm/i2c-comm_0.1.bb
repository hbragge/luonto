DESCRIPTION = "I2C communication module"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"

inherit localmodule update-rc.d

PR = "r2"

# Workaround for error in running do_make_scripts in parallel
DEPENDS = "lpc-fpga"

export INSTALL_MOD_DIR="kernel/drivers/cc-extra"

PKG_${PN} = "kernel-module-${PN}"

do_install_append() {
	install -d ${D}${sysconfdir}/init.d/
	install -m 0755 ${THISDIR}/files/i2c-comm-init ${D}${sysconfdir}/init.d/i2c-comm
}

FILES_kernel-module-${PN} += "${sysconfdir}/init.d/i2c-comm"

INITSCRIPT_PACKAGES="kernel-module-${PN}"
INITSCRIPT_NAME="i2c-comm"
INITSCRIPT_PARAMS="start 11 S . stop 11 0 1 6 ."
