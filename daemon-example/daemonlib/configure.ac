dnl project
AC_PREREQ([2.61])
AC_INIT([daemonlib], 1.0, [some@email.com]) 
AM_INIT_AUTOMAKE([-Wall -Werror foreign])

dnl source
AC_CONFIG_SRCDIR([src/daemonlib_api.c])
AC_CONFIG_HEADERS([config.h])

dnl tools
AC_PROG_CC
AC_PROG_INSTALL
AC_PROG_CC_C_O
AC_PROG_LIBTOOL
AM_PROG_CC_C_O
AM_PROG_AR

AC_ARG_ENABLE([daemon],
  [  --enable-daemon     enable daemon],
  [want_daemon=${enable_val}],
  [want_daemon=yes])

if test "x${want_daemon}" = xyes; then
PKG_CHECK_MODULES([LIBDAEMON], [libdaemon])
fi

AM_CONDITIONAL([COND_DAEMON], [test "$want_daemon" = yes])

dnl Makefiles to generate
AC_CONFIG_FILES([Makefile
		 tool/Makefile
		 daemon/Makefile
                 src/Makefile
		 libdaemonlib.pc])
dnl run
AC_OUTPUT
