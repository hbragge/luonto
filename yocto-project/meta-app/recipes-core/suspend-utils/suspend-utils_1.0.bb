SUMMARY = "Linux Suspend"
DESCRIPTION = "User space tools used for hibernation (suspend-to-disk) and suspend (suspend-to-ram or standby)"
HOMEPAGE = "http://sourceforge.net/projects/suspend/"
SECTION = "console/utils"

LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=a5f46f895a9b1fa92335abf7d76a77dc"

PR = "r8"

SRC_URI = "http://downloads.sourceforge.net/project/suspend/suspend/suspend-${PV}/suspend-utils-${PV}.tar.bz2"

SRC_URI[md5sum] = "02f7d4b679bad1bb294a0efe48ce5934"
SRC_URI[sha256sum] = "0206ba6332860b6da57acc79cc0f8604150ef0835ff9633fd42d59d181a6c85d"

inherit autotools

DEPENDS = "libx86"
RDEPENDS_${PN} = "libx86"

FILES_${PN} += "/usr/lib/suspend/resume"
FILES_${PN}-dbg += "/usr/lib/suspend/.debug/resume"

do_install() {
	install -d ${D}${sysconfdir}/pm/config.d
	echo "SLEEP_MODULE=\"uswsusp\"" > ${D}${sysconfdir}/pm/config.d/00sleep_module
	install -d ${D}${sysconfdir}/pm/config
	echo "S2RAM_OPTS=\"--force\"" > ${D}${sysconfdir}/pm/config/defaults
	echo "QUIRK_NONE=\"true\"" >> ${D}${sysconfdir}/pm/config/defaults

	install -d ${D}${sysconfdir}
	oe_runmake DESTDIR="${D}" install 
}