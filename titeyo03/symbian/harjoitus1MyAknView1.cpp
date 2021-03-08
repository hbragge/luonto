#include "harjoitus1MyAknView1.h"
#include <harjoitus1.rsg>
#include "harjoitus1.hrh"

Charjoitus1MyAknView1* Charjoitus1MyAknView1::NewL()
  {
  Charjoitus1MyAknView1* self = Charjoitus1MyAknView1::NewLC();
  CleanupStack::Pop(self);
  return self;
  }
Charjoitus1MyAknView1* Charjoitus1MyAknView1::NewLC()
  {
  Charjoitus1MyAknView1* self = new (ELeave) Charjoitus1MyAknView1();
  CleanupStack::PushL(self);
  self->ConstructL();
  return self;
  }
Charjoitus1MyAknView1::Charjoitus1MyAknView1()
  {
  }
Charjoitus1MyAknView1::~Charjoitus1MyAknView1()
  {
  }
void Charjoitus1MyAknView1::ConstructL()
  {
  BaseConstructL(R_MY_VIEW1);
  }
  
TUid Charjoitus1MyAknView1::Id() const
  {
  return TUid::Uid(Eharjoitus1View1Id);
  }
void Charjoitus1MyAknView1::DoActivateL(const TVwsViewId& aPrevViewId, TUid aCustomMessageId, const TDesC8& aCustomMessage);