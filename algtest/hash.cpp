#include <iostream>
#include <sstream>
#include <string.h>
using namespace std;

static const int data_len = 200;

struct ValueNode
{
	string key;
	int data;
	ValueNode *next;
	ValueNode() { key = ""; data = 0; next = NULL; };
};

class Hash
{
	ValueNode *valueNodes[data_len];
	int hfunc(string key);
public:
	Hash();
	bool Add(string key, int value);
	bool Get(string key, int *value);
	bool Delete(string key);
};

Hash::Hash()
{
	// initialize data
	for (int i = 0; i < data_len; i++)
		valueNodes[i] = NULL;
}

int Hash::hfunc(string key)
{
	unsigned int hash = 0;
	unsigned int i;
	const char *strkey = key.c_str();

	for (i = 0; i < strlen(strkey); i++)
		hash += strkey[i];

	return (hash % data_len);
}

bool Hash::Add(string key, int value)
{
	int index = hfunc(key);
	ValueNode *newNode;
	ValueNode *valueNode = valueNodes[index];
	ValueNode *prev = NULL;

	// find empty value node
	while (valueNode != NULL) {
		prev = valueNode;
		valueNode = valueNode->next;
	}

	// store into empty value node
	newNode = new ValueNode();
	newNode->key = key;
	newNode->data = value;

	if (prev != NULL) {
		prev->next = newNode;
	} else
		valueNodes[index] = newNode;

	return true;
}

bool Hash::Get(string key, int *value)
{
	int index = hfunc(key);
	ValueNode *valueNode = valueNodes[index];

	while (valueNode) {
		if (valueNode->key == key) {
			*value = valueNode->data;
			return true;
		}

		valueNode = valueNode->next;
	}

	return false;
}

bool Hash::Delete(string key)
{
        int index = hfunc(key);
	ValueNode *valueNode = valueNodes[index];
	ValueNode *prev = NULL;
	
	while (valueNode) {
		if (valueNode->key != key) {
			// wrong node
			prev = valueNode;
		} else {
			// correct node
			if (prev == NULL)
				valueNodes[index] = valueNode->next;
			else
				prev->next = valueNode->next;

			delete valueNode;
			valueNode = NULL;

			return true;
		}

		valueNode = valueNode->next;
	}

	return false;
}

int main(void)
{
	Hash htable;
	string key;
	int value;
	const int valuesToTest = 25;
	bool result = true;
	
	for (int i = 0; i < valuesToTest; i++) {
		std::ostringstream oss;
		oss << i;
		key = "param" + oss.str();
		htable.Add(key, i);
	}

	for (int i = 0; i < valuesToTest; i++) {
		std::ostringstream oss;
		oss << i;
		key = "param" + oss.str();
		if (htable.Get(key, &value)) {
			if (value != i) {
				cout << "Got wrong value for " << key << endl;
				result = false;
			}
		} else {
			cout << "Value not found for " << key << endl;
			result = false;
		}
	}

	for (int i = 0; i < valuesToTest; i++) {
		std::ostringstream oss;
		oss << i;
		key = "param" + oss.str();
		htable.Delete(key);
		if (htable.Get(key, &value)) {
			cout << "Value not deleted for key " << key << endl;
			result = false;
		}
	}

	if (result)
		cout << "Tests passed" << endl;
	else
		cout << "Tests failed" << endl;

	return 0;
}
