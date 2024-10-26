require recipes-kernel/linux/linux-yocto.inc

KBRANCH = "yocto/standard/base"
KBRANCH_qemux86  = "yocto/standard/common-pc/base"
KBRANCH_qemux86-64  = "yocto/standard/common-pc-64/base"
KBRANCH_qemuppc  = "yocto/standard/qemu-ppc32"
KBRANCH_qemumips = "yocto/standard/mti-malta32-be"
KBRANCH_qemuarm  = "yocto/standard/arm-versatile-926ejs"

KMETA = "meta"

LINUX_VERSION ?= "3.0.32"

SRCREV_machine_qemuarm ?= "f2d606dfbc61d10847783f1e7c1dcb0ecabf3220"
SRCREV_machine_qemumips  ?= "57f4bbfb4c65e4c8e349401b877f1661fb026ed6"
SRCREV_machine_qemuppc ?= "d8779a6245d13c3b56eabac36a14c8896f448566"
SRCREV_machine_qemux86 ?= "33d5d1ea371dad280e5bcfadd12c3a360c6bc5b8"
SRCREV_machine_qemux86-64 ?= "fe23c7dd94eb94dd5887028683093615ac921086"
SRCREV_machine ?= "cef17a18d72eae749dc78de3c83772f52815d842"
SRCREV_meta ?= "bf5ee4945ee6d748e6abe16356f2357f76b5e2f0"

PR = "${INC_PR}.1"
PV = "${LINUX_VERSION}+git${SRCPV}"

SRC_URI = "git://git.yoctoproject.org/linux-yocto-3.0;protocol=git;bareclone=1;branch=${KBRANCH},meta;name=machine,meta"

COMPATIBLE_MACHINE = "qemuarm|qemux86|qemuppc|qemumips|qemux86-64"

# Functionality flags
KERNEL_FEATURES_append = " features/netfilter/netfilter.scc"
KERNEL_FEATURES_append = " features/taskstats/taskstats.scc"
KERNEL_FEATURES_append_qemux86 = " cfg/sound.scc"
KERNEL_FEATURES_append_qemux86-64 = " cfg/sound.scc"

SRC_URI_cedartrail = "git://git.yoctoproject.org/linux-yocto-3.0;protocol=git;bareclone=1;branch=${KBRANCH},meta,yocto/pvr;name=machine,meta,pvr"

SRC_URI_cedartrail-nopvr = "git://git.yoctoproject.org/linux-yocto-3.0;protocol=git;nocheckout=1;branch=${KBRANCH},meta;name=machine,meta"

COMPATIBLE_MACHINE_cedartrail = "cedartrail"
KMACHINE_cedartrail  = "cedartrail"
KBRANCH_cedartrail  = "yocto/standard/cedartrail"
KERNEL_FEATURES_append_cedartrail += "bsp/cedartrail/cedartrail-pvr-merge.scc"
KERNEL_FEATURES_append_cedartrail += "cfg/efi-ext.scc"

COMPATIBLE_MACHINE_cedartrail-nopvr = "cedartrail"
KMACHINE_cedartrail-nopvr  = "cedartrail"
KBRANCH_cedartrail-nopvr  = "yocto/standard/cedartrail"
KERNEL_FEATURES_append_cedartrail-nopvr += " cfg/smp.scc"

SRCREV_machine_pn-linux-yocto_cedartrail ?= "1e79e03d115ed177882ab53909a4f3555e434833"
SRCREV_meta_pn-linux-yocto_cedartrail ?= "bf5ee4945ee6d748e6abe16356f2357f76b5e2f0"
SRCREV_pvr_pn-linux-yocto_cedartrail ?= "7828ab82533828b924dbfad5158e274a8bb04df3"

SRCREV_machine_pn-linux-yocto_cedartrail-nopvr ?= "1e79e03d115ed177882ab53909a4f3555e434833"
SRCREV_meta_pn-linux-yocto_cedartrail-nopvr ?= "bf5ee4945ee6d748e6abe16356f2357f76b5e2f0"
