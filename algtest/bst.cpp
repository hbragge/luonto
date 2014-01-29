#include <iostream>
using namespace std;

class Bst
{
	Bst *parent;
	Bst *left;
	Bst *right;
	int value;
public:
	Bst(Bst *p, int v);
	Bst *GetParent() { return parent; };
	Bst *GetLeft() { return left; };
	Bst *GetRight() { return right; };
	void SetLeft(Bst *l) { left = l; };
	void SetRight(Bst *r) { right = r; };
	int GetValue();
	void Add(int v);
	Bst *Find(int v);
	void Delete(int v);
	void PrintInOrder();
};

Bst::Bst(Bst *p, int v)
{
	parent = p;
	value = v;
}

int Bst::GetValue()
{
	return value;
}

void Bst::Add(int v)
{
	if (v < value) {
		if (left == NULL)
			left = new Bst(this, v);
		else
			left->Add(v);
	} else if (v > value) {
		if (right == NULL)
			right = new Bst(this, v);
		else
			right->Add(v);		
	}
}

Bst *Bst::Find(int v)
{
	if (v < value) {
		if (left != NULL)
			return left->Find(v);
		else
			return NULL;
	} else if (v > value) {
		if (right != NULL)
			return right->Find(v);
		else
			return NULL;
	} else // v == value
		return this;
}

void Bst::Delete(int v)
{
	Bst *node = Find(v);
	if (node == NULL)
		return; // not found

	// only update parent if not root
	if (node->GetParent() != NULL) {
		node->GetParent()->SetLeft(node->GetLeft());
		node->GetParent()->SetRight(node->GetRight());
	}

	delete node;
}

void Bst::PrintInOrder()
{
	if (left != NULL) {
		cout << "l "; left->PrintInOrder();
	}
	cout << value << endl;
	if (right != NULL) {
		cout << "r "; right->PrintInOrder();
	}
}

int main(void)
{
	Bst *root = new Bst(NULL, 20);
	root->Add(4);
	root->Add(8);
	root->Add(14);
	root->Add(22);
	root->PrintInOrder();

	cout << "Find 14: " << root->Find(14)->GetValue() << endl;

	root->Delete(8);
	root->PrintInOrder();
	
	return 0;
}
