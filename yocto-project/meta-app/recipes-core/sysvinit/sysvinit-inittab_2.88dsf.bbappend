PR := "${PR}.1"

# Replace default runlevel with 3
# DO NOT CHANGE. Use default run level 3 on ALL devices
do_install_append() {
	sed -i -r 's/^id:[0-6]+:/id:3:/' ${D}${sysconfdir}/inittab
}
