SUMMARY = "Hardware independed library"
DESCRIPTION = "A hardware-independent library for executing real-mode x86 code"
HOMEPAGE = "http://www.codon.org.uk/~mjg59/libx86/"
SECTION = "console/utils"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=633af6c02e6f624d4c472d970a2aca53"

PR = "r2"

SRC_URI = "http://www.codon.org.uk/~mjg59/libx86/downloads/${BPN}-${PV}.tar.gz \
	file://libx86-1.1-IF_MASK-undeclared.patch"

SRC_URI[md5sum] = "41bee1f8e22b82d82b5f7d7ba51abc2a"
SRC_URI[sha256sum] = "5bf13104cb327472b5cb65643352a9138646becacc06763088d83001d832d048"

inherit autotools
