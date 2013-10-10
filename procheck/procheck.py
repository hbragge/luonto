#!/usr/bin/python

# Procheck - a simple TODO list

__author__ = "Henri Bragge"
__version__ = "$Revision: 0.1 $"
__date__ = "$Date: 2013/10/10 15:32:31 $"
__copyright__ = "BSD"
__license__ = "BSD"

import sys

actionList = []
commandPrompt = "procheck # "
maxTitleLen = 10
maxDescrLen = 25

class Action:
        "a base class for action handlers"
        viewable = True
        key = ''
        
        def IsMe(self, cmd):
                if cmd in (self.key, self.key.swapcase()):
                        return True
                else:
                        return False

        def HandleAction(self, cmd, bugList):
                return True

        def Handle(self, cmd, bugList):
                if not self.IsMe(cmd):
                        return False

                return self.HandleAction(cmd, bugList)

               
        def Confirm(self):
                resp = raw_input("Are you sure (y/n): ")

                if resp in ('y', 'Y'):
                        return True
                else:
                        return False

class ActionExit(Action):
        "exit program"
        key = 'q'

        def HandleAction(self, cmd, bugList):
                if not self.IsMe(cmd):
                        return False
                
                sys.exit(0)

class ActionSaveToFile(Action):
        "save project into a file"
        key = 's'
        def HandleAction(self, cmd, bugList):
                if not self.IsMe(cmd):
                        return False

                filename = raw_input("Filename: ")
                
                file = open(filename, 'w')
                file.write(ActionListBugs().BugListToString())
                file.close()

                print "File saved"

                return True

class ActionLoadFromFile(Action):
        "load project from a file"
        key = 'd'
        def ParseData(self, data):
                arrayData = data.split()
                p = 0                

                if arrayData:
                        while (p < len(arrayData)):
                                i = int(arrayData[p])
                                p += 1
                                if (p > len(arrayData)-1):
                                        break

                                title = arrayData[p]
                                p += 1
                                if (p > len(arrayData)-1):
                                        break

                                description = arrayData[p]
                                p += 1        
                
                                tmpBug = Bug(title, description)

                                if (p < len(arrayData)):
                                        if arrayData[p] == "RESOLVED":
                                                tmpBug.checked = True
                                                p += 1
                                        elif arrayData[p] == "OBSOLETE":
                                                tmpBug.obsolete = True
                                                p += 1
                                bugList.append(tmpBug)
                        

        def HandleAction(self, cmd, bugList):
                if not self.IsMe(cmd):
                        return False

                filename = raw_input("Filename: ")

                if not self.Confirm():
                        return True

                file = open(filename, 'r')
                data = file.read()
                file.close()

                self.ParseData(data)
                
                print "File loaded"

                return True               

class ActionListBugs(Action):
        "list bugs"
        key = 'l'
        def BugListToString(self):
                bugStringList = []
                for bug in bugList:
                                status = ""
                                if bug.checked:
                                        status = "RESOLVED"
                                # obsolete overrides checked
                                if bug.obsolete:
                                        status = "OBSOLETE"

                                bugStringList.append("%i %s %s %s" % (bugList.index(bug), 
                                                bug.title.ljust(maxTitleLen), bug.description.ljust(maxDescrLen), status))

                return "\n".join(bugStringList)

        def HandleAction(self, cmd, bugList):
                if not self.IsMe(cmd):
                        return False

                if len(bugList) == 0:
                        print "no bugs"
                else:
                        print self.BugListToString()

                return True

class ActionCreateBug(Action):
        "create a new bug"
        key = 'n'
        def HandleAction(self, cmd, bugList):
                if not self.IsMe(cmd):
                        return False

                title = raw_input("Bug title (max %s characters): " %maxTitleLen)

                while (len(title) == 0 or len(title) > maxTitleLen):
                        title = raw_input("Bug title (max %s characters): " %maxTitleLen)

                description = raw_input("Bug description (max %s characters): " %maxDescrLen)

                while (len(description) == 0 or len(description) > maxDescrLen):
                        description = raw_input("Bug description (max %s characters): " %maxDescrLen)
                
                bug = Bug(title, description)
                bugList.append(bug)

                return True

class ActionRemoveBug(Action):
        "remove a bug"
        key = 'r'
        def HandleAction(self, cmd, bugList):
                if not self.IsMe(cmd):
                        return False

                num = raw_input("Bug number (a for all): ")

                if num == 'a':
                        bugList = []
                        return True

                if num:
                        num = int(num)
                else:
                        return True

                if num < 0:
                        return True

                if num < len(bugList):
                        if not self.Confirm():
                                return True
                        bugList.pop(num)
                else:
                        print "no such bug"

                return True


class ActionCheck(Action):
        "check/uncheck a bug"
        key = 'c'
        def HandleAction(self, cmd, bugList):
                if not self.IsMe(cmd):
                        return False

                num = raw_input("Bug number: ")

                if num:
                        num = int(num)
                else:
                        return True

                if num < 0:
                        return True

                if num < len(bugList):
                        bugList[num].Check()
                else:
                        print "no such bug"

                return True

class ActionObsolete(Action):
        "obsolete/unobsolete a bug"
        key = 'o'
        def HandleAction(self, cmd, bugList):
                if not self.IsMe(cmd):
                        return False

                num = raw_input("Bug number: ")

                if num:
                        num = int(num)
                else:
                        return True

                if num < len(bugList):
                        bugList[num].Obsolete()
                else:
                        print "no such bug"

                return True


class ActionUnknown(Action):
        "unknown command"
        viewable = False

        def Handle(self, cmd, bugList):
                return self.HandleAction(cmd, bugList)

        def HandleAction(self, cmd, bugList):
                if cmd:
                        print "Unknown command '%s'" %cmd

class ActionHelp(Action):
        "print help"
        key = 'h'
        def HandleAction(self, cmd, bugList):
                if not self.IsMe(cmd):
                        return False
                
                print "procheck is a project management tool" \
                      " for bug tracking and checking"

                for action in actionList:
                        if action.viewable:
                                print "%s - %s" % (action.key, action.__doc__)

                return True


class Bug:
        "bug"
        title = ""
        description = ""
        checked = False
        obsolete = False
        def __init__(self, title, description):
                self.title = title
                self.description = description        

        def Check(self):
                if self.checked:
                        self.checked = False
                else:
                        self.checked = True

        def Obsolete(self):
                if self.obsolete:
                        self.obsolete = False
                else:
                        self.obsolete = True


if __name__ == '__main__':
        if len(sys.argv) == 2:
                if sys.argv[1] == '--h':
                        print "procheck is a project management tool" \
                              " for bug tracking and checking"
                        sys.exit(0)

        actionList = [ActionHelp, ActionListBugs, 
                      ActionCreateBug, ActionRemoveBug, ActionCheck, ActionObsolete, 
                      ActionSaveToFile, ActionLoadFromFile, ActionExit, ActionUnknown]
        bugList = []

        while 1:
                cmd = raw_input(commandPrompt)        

                for action in actionList:
                        if action().Handle(cmd, bugList):
                                break
        
        

