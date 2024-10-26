FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:${THISDIR}/files:"

COMPATIBLE_MACHINE_xm = "xm"
KMACHINE_xm = "emenlow"
KBRANCH_xm = "standard/emenlow"
KERNEL_FEATURES_append_xm = " features/drm-emgd/drm-emgd-1.16 cfg/vesafb"
SRCREV_meta_xm = "2a6d36e75ca0a121570a389d7bab76ec240cbfda"
SRCREV_machine_xm = "47aed0c17c1c55988198ad39f86ae88894c8e0a4"
SRCREV_emgd_xm = "c780732f175ff0ec866fac2130175876b519b576"
SRC_URI_xm = "git://git.yoctoproject.org/linux-yocto-3.8.git;protocol=git;nocheckout=1;branch=${KBRANCH},${KMETA},emgd-1.16;name=machine,meta,emgd"

SRC_URI_append = " file://logo.cfg"
SRC_URI_append = " file://logo.patch"
SRC_URI_append = " file://cc-misc.cfg"
