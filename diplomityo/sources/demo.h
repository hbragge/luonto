
#ifndef HTTPD_DEMO_TEMPLATE
#define HTTPD_DEMO_TEMPLATE

#include "control.h"
#include "request.h"

class DemoHandler : public HttpdFileHandler
{
   protected:
    bool TranslateUri(RequestState &state);
    void SendFile(RequestState &state);
   
 public:
    DemoHandler(HttpdFileSystem *p_filesys,
                 const char     *p_root,
                 const char     *p_prefix);
    private:
     CControl *p_control;
     CControl *m_pControl;
     CRequest *m_req;
     CDispatcher *dp;
     Content *p_content;
};

#endif

