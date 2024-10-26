SUMMARY = "Adding default users"
DESCRIPTION = "This recipe adds default users to Linux"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"


SRC_URI = "file://${THISDIR}/files/file1 \
	file://${THISDIR}/files/passwd.orig \
	file://${THISDIR}/files/shadow.orig"


inherit useradd

# You must set USERADD_PACKAGES when you inherit useradd. This
# lists which output packages will include the user/group
# creation code.
USERADD_PACKAGES = "${PN}"

# You must also set USERADD_PARAM and/or GROUPADD_PARAM when
# you inherit useradd.

# USERADD_PARAM specifies command line options to pass to the
# useradd command. Multiple users can be created by separating
# the commands with a semicolon. 
USERADD_PARAM_${PN} = "-u 1200 -d /home/ccs -r -s /bin/bash ccs"

# GROUPADD_PARAM works the same way, which you set to the options
# you'd normally pass to the groupadd command.
#GROUPADD_PARAM_${PN} = "-g 880 ccs"

do_install () {
	install -d -m 755 ${D}/home/ccs
	install -p -m 644 ${THISDIR}/files/file1 ${D}/home/ccs/

	# The new users and groups are created before the do_install
	# step, so you are now free to make use of them:
	chown -R ccs ${D}/home/ccs

	chgrp -R ccs ${D}/home/ccs

	# install new passwd and shadow files
	install -d -m 755 ${D}${sysconfdir}
	install -p -m 644 ${THISDIR}/files/passwd.orig ${D}${sysconfdir}
	install -p -m 400 ${THISDIR}/files/shadow.orig ${D}${sysconfdir}
}

FILES_${PN} = "/home/ccs/* ${sysconfdir}/*"

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
