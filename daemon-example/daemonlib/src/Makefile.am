lib_LTLIBRARIES = libdaemonlib.la

libdaemonlib_la_CPPFLAGS = \
	-I${top_srcdir}/src

libdaemonlib_la_CFLAGS = \
	-Wall \
	-Werror \
	-D_GNU_SOURCE

libdaemonlib_la_SOURCES = \
	daemonlib_api.c

include_HEADERS = \
	daemonlib_api.h

noinst_HEADERS = \
	daemonlib_private.h
