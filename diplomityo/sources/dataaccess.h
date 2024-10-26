#ifndef CDATAACCESS_H
#define CDATAACCESS_H

class CAction;
class CRequest;
class CDispatcher;
class error_t;

class CDataAccess {
 public:
  CDataAccess();
  ~CDataAccess();
  error_t FindAll(const CAction *acn, CRequest *req, CDispatcher *dp) const;
};

#endif
