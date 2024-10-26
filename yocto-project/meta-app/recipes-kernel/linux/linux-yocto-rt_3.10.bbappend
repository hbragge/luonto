FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:${THISDIR}/files:"

COMPATIBLE_MACHINE_xm = "xm"
KMACHINE_xm = "emenlow"
KBRANCH_xm = "standard/emenlow"
KERNEL_FEATURES_append_xm = " features/drm-emgd/drm-emgd-1.18 cfg/vesafb"
SRCREV_meta_xm = "452f0679ea93a6cb4433bebd7177629228a5cf68"
SRCREV_machine_xm = "2927821e14523fa0ee18140aa7ff6e0509b48ab7"
SRCREV_emgd_xm = "39c44dd7838bfd228938219cdb21ca30c4d0cbbf"
SRC_URI_xm = "git://git.yoctoproject.org/linux-yocto-3.10.git;protocol=git;nocheckout=1;branch=${KBRANCH},${KMETA},emgd-1.18;name=machine,meta,emgd"

SRC_URI_append = " file://logo.cfg"
SRC_URI_append = " file://logo.patch"
SRC_URI_append = " file://cc-misc.cfg"

SRC_URI_append = " file://cc-can.cfg"
SRC_URI_append = " file://xilinx-can.patch"
SRC_URI_append = " file://xilinx-can.cfg"

SRC_URI_append = " file://evdev.cfg"