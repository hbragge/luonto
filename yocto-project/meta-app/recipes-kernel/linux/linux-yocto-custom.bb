inherit kernel
require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:${THISDIR}/files:"
COMPATIBLE_MACHINE_xm-ng = "xm-ng"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;protocol=git;bareclone=1"
SRC_URI += "file://logo.patch"
SRC_URI += "file://defconfig"

PR = "r2"
KBRANCH = "linux-3.7.y"
LINUX_VERSION = "3.7.8"
LINUX_VERSION_EXTENSION = "-custom"

SRCREV="7773647c3ea250586c2ee44559f6f1bdc2a5c885"

PV = "${LINUX_VERSION}+git${SRCPV}"

# drm.ko must be built so that kernel will be configured correctly,
# but we must remove the file so that it can be replaced by cedarview-drm
do_removedrm() {
	rm -f ${D}/lib/modules/${LINUX_VERSION}${LINUX_VERSION_EXTENSION}/kernel/drivers/gpu/drm/drm.ko
}

addtask removedrm before do_package after do_install
