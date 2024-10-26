FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:${THISDIR}/files:"

PR := "${PR}.2"

# Revision of imx_2.6.35_maintain branch
SRCREV_xa = "e6b3f3bb3ec6bf92f6b14e8c198b8162061408b5"
LOCALVERSION_xa = "-maintain+yocto"

SRC_URI_append_xa = " \
           file://linux-2.6.35.3.dual-tvp5150-support.patch \
           file://linux-2.6.35.3.fix-24bit-colors.patch \
           file://linux-2.6.35.3.fix-ipu-channel-disable.patch \
           file://linux-2.6.35.3.fix-v4l2-output-get-rot.patch \
           file://linux-2.6.35.3.ipu-backport-fixes.patch \
           file://linux-2.6.35.3.sgtl5000-fixed-vdd-settings.patch \
           file://linux-2.6.35.3.tipswitch2-remap.patch \
           file://revert-ldb-patch.patch \
           file://bsp-linux-2.6.35.3-xa2.patch \
           file://defconfig \
"
