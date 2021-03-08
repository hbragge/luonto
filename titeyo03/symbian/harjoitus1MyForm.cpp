#include "harjoitus1MyForm.h"
#include "harjoitus1.hrh"
#include <eikenv.h>
#include <aknslider.h> //CAknSlider
#include <eikmenup.h> // R_AVKON_FORM_MENUPANE
#include <Avkon.rsg>
#include <avkon.hrh>  //EAknFormCmdLabel, etc...

Charjoitus1MyForm::Charjoitus1MyForm(Charjoitus1AppView* aView):iView(aView)
  {
  }

Charjoitus1MyForm::~Charjoitus1MyForm()
  {
  }

Charjoitus1MyForm* Charjoitus1MyForm::NewL(Charjoitus1AppView* aView)
  {
  Charjoitus1MyForm* self = new (ELeave)Charjoitus1MyForm(aView);
  CleanupStack::PushL(self);
  self->ConstructL();
  CleanupStack::Pop();
  return self;
  }

TBool Charjoitus1MyForm::SaveFormDataL()
  {
  CAknSlider* sliderX = static_cast<CAknSlider*>(ControlOrNull(EMyFormSliderXId));
  CAknSlider* sliderY = static_cast<CAknSlider*>(ControlOrNull(EMyFormSliderYId));

  TInt x = sliderX->Value();
  TInt y = sliderY->Value();
  iView->SetBallPosition(x,y);
  return ETrue;
  }

void Charjoitus1MyForm::DoNotSaveFormDataL()
  {
  LoadValuesFromDataL();
  }
  
void Charjoitus1MyForm::LoadValuesFromDataL()
  {
  CAknSlider* sliderX = static_cast<CAknSlider*>(ControlOrNull(EMyFormSliderXId));
  CAknSlider* sliderY = static_cast<CAknSlider*>(ControlOrNull(EMyFormSliderYId));
  TInt x,y;
  iView->MaxValues(x,y); //Luetaan max-arvot x- ja y-muuttujiin
  sliderX->SetRange(0,x);
  sliderY->SetRange(0,y);
  iView->BallPosition(x,y); //Luetaan pallon paikka x- ja y-muuttujiin
  sliderX->SetValueL(x);
  sliderY->SetValueL(y);
  }
  
void Charjoitus1MyForm::PreLayoutDynInitL()
  {
  CAknForm::PreLayoutDynInitL(); // Kutsutaan kantaluokan dynittiä ensin
  LoadValuesFromDataL();
  }

void Charjoitus1MyForm::DynInitMenuPaneL(TInt aResId, CEikMenuPane* aMenu)
  {
  CAknForm::DynInitMenuPaneL(aResId,aMenu);
  if (aResId == R_AVKON_FORM_MENUPANE)
    {
    aMenu->SetItemDimmed(EAknFormCmdLabel, ETrue);
	aMenu->SetItemDimmed(EAknFormCmdAdd, ETrue);
	aMenu->SetItemDimmed(EAknFormCmdDelete, ETrue);
    }
  }

void Charjoitus1MyForm::HandleControlEventL(CCoeControl* aControl, TCoeEvent aEventType)
  {
  CAknForm::HandleControlEventL(aControl,aEventType);
  DrawNow();
  }