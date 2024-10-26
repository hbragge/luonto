#include "parser.h"

CParser::CParser()
{
  CompileRegularExpressions();
}

CParser::~CParser()
{
  //printf("Deleting CParseTree\n");
  //fflush(NULL);
  
  if( m_ReInt != NULL )
    {
      free( m_ReInt );
      m_ReInt = 0;
    }
  
  if( m_ReStr != NULL )
    {
      free( m_ReStr );
      m_ReStr = 0;
    }
  
  if( m_ReDescr != NULL )
    {
      free( m_ReDescr );
      m_ReDescr = 0;
    }
  if( m_ReKeywordAttrName != NULL )
    {
      free( m_ReKeywordAttrName );
      m_ReKeywordAttrName = 0;
    }
  
  if( m_ReExplanation != NULL )
    {
      free( m_ReExplanation );
      m_ReExplanation = 0;
    }
  
  if( m_ReAdditionalAttr != NULL )
    {
      free( m_ReAdditionalAttr );
      m_ReAdditionalAttr = 0;
    }
  
  if( m_ReIPV4P != NULL )
    {   
      free( m_ReIPV4P );
      m_ReIPV4P = 0;
    }
  
  if( m_ReURL != NULL )
    {   
      free( m_ReURL );
      m_ReURL = 0;
    }
  if( m_ReCnmAddr != NULL )
    {
      free( m_ReCnmAddr );
      m_ReCnmAddr = 0;
    }
  if( m_ReEmail != NULL )
    {
      free( m_ReEmail );
      m_ReEmail = 0;
    }
}

pcre* CParser::GetRegularExpression(enum regExpr re)
{
  switch(re){
  case RE_INT:
    return m_ReInt;
  case RE_STR:
    return m_ReStr;
  case RE_DESCR:
    return m_ReDescr;
  case RE_KEYWORD_ATTR_NAME:
    return m_ReKeywordAttrName;
  case RE_EXPLANATION:
    return m_ReExplanation;
  case RE_ADDITIONAL_ATTR:
    return m_ReAdditionalAttr;
  case RE_IPV4:
    return m_ReIPV4P;
  case RE_URL:
    return m_ReURL;
  case RE_CNMADDR:
    return m_ReCnmAddr;
  case RE_EMAIL:
    return m_ReEmail;
  default:
    return NULL;
  }
}

pcre *CParser::GetRegularExpressionForAttribute(string *attributeType)
{
  if( attributeType->compare("Integer") == 0 )
    {   
      return m_ReInt;
    }
  else if( attributeType->compare("String") == 0 )
    {
      return m_ReStr;
    }
  else if( attributeType->compare("IPv4") == 0 )
    {
      return m_ReIPV4P;
    }
  else
    {   
      return NULL;
    }
}

bool CParser::CompileRegularExpressions()
{
  const char *error;
  int erroffset;
  
  m_ReInt = pcre_compile("([0-9])+", 0, &error, &erroffset, NULL );
  m_ReStr = pcre_compile("([-_A-Za-z0-9])+", 0, &error, &erroffset, NULL );
  m_ReDescr = pcre_compile("([-_/.,%()A-Za-z0-9])+([- _/.,%()A-Za-z0-9])*", 0, &error, &erroffset, NULL );
  //m_Re3 = pcre_compile("([$])([-_A-Za-z0-9])+=", 0, &error, &erroffset, NULL );
  m_ReKeywordAttrName = pcre_compile("(([$])([-_A-Za-z0-9])+=)?([-_A-Za-z0-9])+", 0, &error, &erroffset, NULL );
  m_ReExplanation = pcre_compile("([- _/.,%()A-Za-z0-9])+", 0, &error, &erroffset, NULL );
  //m_Re5 = pcre_compile("($([-_A-Za-z0-9])+$)?([-_A-Za-z0-9])+", 0, &error, &erroffset, NULL );
  m_ReAdditionalAttr = pcre_compile("(([$])([-_A-Za-z0-9])+=)([-_/.:A-Za-z0-9])+", 0, &error, &erroffset, NULL );
  // get better ipv4 prefix regex?
  m_ReIPV4P = pcre_compile("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+(/[0-9]+)?", 0, &error, &erroffset, NULL );
  //m_ReIPV4P = pcre_compile(".*", 0, &error, &erroffset, NULL );
  //m_ReIPV4P = pcre_compile("([/.0-9])+", 0, &error, &erroffset, NULL );
  m_ReURL = pcre_compile("([-~@%=&$!_/,.;:A-Za-z0-9])+", 0, &error, &erroffset, NULL );
  m_ReCnmAddr = pcre_compile("([/:0-9])+", 0, &error, &erroffset, NULL );
  m_ReEmail = pcre_compile("([-_.@A-Za-z0-9])+", 0, &error, &erroffset, NULL );
  
  /*argv[1],     // the pattern
    0,           // default options
    &error,      // for error message
    &erroffset,  // for error offset
    NULL);       // use default character tables*/
  
  if( m_ReInt == NULL || m_ReStr == NULL || m_ReDescr == NULL || m_ReKeywordAttrName == NULL ||
      m_ReExplanation == NULL || m_ReAdditionalAttr == NULL || m_ReIPV4P == NULL ||
      m_ReURL == NULL || m_ReCnmAddr == NULL || m_ReEmail == NULL )
    {   
      return false;
    }
    
  return true;
}

