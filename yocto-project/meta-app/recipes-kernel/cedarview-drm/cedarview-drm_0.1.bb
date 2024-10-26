DESCRIPTION = "DRM and graphics modules for Cedarview"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"

inherit localmodule

PR = "r0"

# Workaround for error in running do_make_scripts in parallel
DEPENDS = "socket-can"

export INSTALL_MOD_DIR="kernel/drivers/gpu/drm"

PKG_${PN} = "kernel-module-${PN}"
