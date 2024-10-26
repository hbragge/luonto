AM_CPPFLAGS = -I${top_srcdir}/src
LDADD = ../src/libdaemonlib.la ${LIBDAEMON_LIBS}
AM_CFLAGS = \
	${LIBDAEMON_CFLAGS} \
	-Wall \
	-Werror \
	-D_GNU_SOURCE

bin_PROGRAMS = daemonlibd
