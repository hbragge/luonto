SUMMARY = "Text conversion tool"
DESCRIPTION = "Test conversion tool between MSDOS and Linux"
HOMEPAGE = "http://www.thefreecountry.com/tofrodos/"
SECTION = "console/utils"

# Tofrodos unpacks to tofrodos - directory instead of the normal
# tofrodos-1.7.13 - directory so some fiddling is needed.
S = "${WORKDIR}/tofrodos/src"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://../COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

PR = "r1"

SRC_URI = "http://tofrodos.sourceforge.net/download/${BPN}-${PV}.tar.gz"

SRC_URI[md5sum] = "c4c5e6668a13a01bfb5ce562753a808f"
SRC_URI[sha256sum] = "3457f6f3e47dd8c6704049cef81cb0c5a35cc32df9fe800b5fbb470804f0885f"

inherit autotools gettext

# Due bad makefile few variables needs to be overridden.
EXTRA_OEMAKE_xm = " \
 CC=i686-pc-linux-gnu-gcc \
 LDEBUG= \
"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 fromdos ${D}${bindir}
	install -m 0755 todos ${D}${bindir}
}