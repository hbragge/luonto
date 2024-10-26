DESCRIPTION = "Utilities for working with generic SCSI devices"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=1cc481c050dc92e12db9c8145043d5dc"
PR = "r0"

inherit autotools externalsrc

EXTERNALSRC = "${COREBASE}/../apps/appauxd/sg3_utils-${PV}"
EXTERNALSRC_BUILD = "${EXTERNALSRC}"

TARGET_CC_ARCH += "${LDFLAGS}"
TARGET_PREFIX_xm = "i686-pc-linux-gnu-"
TARGET_PREFIX_xm-ng = "i686-pc-linux-gnu-"

SRC_URI[md5sum] = "261e39f7161337330ba6bd57999be801"
SRC_URI[sha256sum] = "3034a4e798404cc963fc46437b0ceeb0edc635e02471ab13aa18acd8b716a27b"
