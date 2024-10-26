DESCRIPTION = "Socket CAN communication module"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d85064f0fa931974668d281ab83cc97e"

inherit localmodule

PR = "r7"

# Workaround for error in running do_make_scripts in parallel
DEPENDS = "jidadrv"

export INSTALL_MOD_DIR="kernel/drivers/extra"

PKG_${PN} = "kernel-module-${PN}"

EXTRA_OEMAKE = "-C kernel/2.6"

do_install() {
	install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/extra/
 	install -m 0644 kernel/2.6/drivers/net/can/can-dev.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/extra
 	install -m 0644 kernel/2.6/drivers/net/can/xilinx/xilinx.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/extra
 	install -m 0644 kernel/2.6/drivers/net/can/xilinx/xilinx_pci.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/extra
 	install -m 0644 kernel/2.6/net/can/can.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/extra
 	install -m 0644 kernel/2.6/net/can/can-raw.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/extra
 	install -m 0644 kernel/2.6/net/can/can-bcm.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/extra
 	install -m 0644 kernel/2.6/net/can/can-isotp.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/extra
}
