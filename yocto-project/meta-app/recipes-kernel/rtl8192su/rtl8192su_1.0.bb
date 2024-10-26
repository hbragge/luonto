DESCRIPTION = "WIFIHU USB driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"

inherit localmodule

COMPATIBLE_MACHINE = "xm"

PR = "r9"

export INSTALL_MOD_DIR="kernel/drivers/cc-extra"

PKG_${PN} = "kernel-module-${PN}"

RDEPENDS_${PN} = "rtlwifi-firmware"