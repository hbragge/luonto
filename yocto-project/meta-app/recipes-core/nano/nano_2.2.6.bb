SUMMARY = "Text Editor"
DESCRIPTION = "Nano is small text editor"
HOMEPAGE = "http://www.nano-editor.org/"
SECTION = "console/utils"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=f27defe1e96c2e1ecd4e0c9be8967949"

PR = "r1"

SRC_URI = "http://www.nano-editor.org/dist/v2.2/${BPN}-${PV}.tar.gz"

SRC_URI[md5sum] = "03233ae480689a008eb98feb1b599807"
SRC_URI[sha256sum] = "be68e133b5e81df41873d32c517b3e5950770c00fc5f4dd23810cd635abce67a"

inherit autotools gettext

#do_install () {
#        oe_runmake 'bindir=${D}${bindir}' 'mandir=${D}${mandir}' install
#}

#ALTERNATIVE_${PN} = "less"
#ALTERNATIVE_PRIORITY = "100"
