#ifndef PARSER_H
#define PARSER_H

#include <map>
#include <list>
#include <string>
using namespace std;
#include <pcre.h>

enum regExpr
{
    RE_INT = 1,
    RE_STR,
    RE_KEYWORD_ATTR_NAME,
    RE_EXPLANATION,
    RE_ADDITIONAL_ATTR,
    RE_IPV4,
    RE_URL,
    RE_CNMADDR,
    RE_EMAIL,
    RE_DESCR
};

class CParser {
 public:
  CParser();
  ~CParser();
  bool CompileRegularExpressions();
  pcre *GetRegularExpression(enum regExpr re);
  pcre *GetRegularExpressionForAttribute(string *valueType);
 protected:
  pcre *m_ReInt;
  pcre *m_ReStr;
  pcre *m_ReDescr;
  pcre *m_ReKeywordAttrName;
  pcre *m_ReExplanation;
  pcre *m_ReAdditionalAttr;
  pcre *m_ReIPV4P;
  pcre *m_ReURL;
  pcre *m_ReCnmAddr;
  pcre *m_ReEmail;
};

#endif
