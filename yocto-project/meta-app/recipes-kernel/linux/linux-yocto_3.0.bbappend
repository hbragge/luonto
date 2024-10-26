FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:${THISDIR}/files:"

COMPATIBLE_MACHINE_xm-ng = "xm-ng"
KMACHINE_xm-ng  = "cedartrail"
KBRANCH_xm-ng  = "standard/cedartrail"
KERNEL_FEATURES_append_xm-ng += "bsp/cedartrail/cedartrail-pvr-merge.scc"
KERNEL_FEATURES_append_xm-ng += "cfg/efi-ext.scc"

SRC_URI_append = " file://cc-can.cfg"
SRC_URI_append = " file://xilinx-can.patch"
SRC_URI_append = " file://xilinx-can.cfg"
