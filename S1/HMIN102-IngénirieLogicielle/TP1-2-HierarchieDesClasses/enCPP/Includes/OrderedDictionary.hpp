#ifndef ORDEREDDICTIONARY_HPP
#define ORDEREDDICTIONARY_HPP

#include "AbstractDictionary.hpp"

template<typename T>
class OrderedDictionary: public AbstractDictionary<T> {

	public:

		OrderedDictionary();
		OrderedDictionary(const unsigned int size);

	protected:

		unsigned int newIndex(const std::string &key);
		int findIndex(const std::string &key) const;
};





template<typename T>
OrderedDictionary<T>::OrderedDictionary() {}

template<typename T>
OrderedDictionary<T>::OrderedDictionary(const unsigned int size) { AbstractDictionary<T>::m_dictionary.reserve(size); }

template<typename T>
unsigned int OrderedDictionary<T>::newIndex(const std::string &key) {

	if(AbstractDictionary<T>::m_nbElements == AbstractDictionary<T>::m_dictionary.size()) { AbstractDictionary<T>::m_dictionary.emplace_back(std::make_pair("", T{})); }

	AbstractDictionary<T>::m_nbElements++;

	return AbstractDictionary<T>::m_nbElements-1;
}

template<typename T>
int OrderedDictionary<T>::findIndex(const std::string &key) const {

	for(int i{0}; i < static_cast<int>(AbstractDictionary<T>::m_dictionary.size()); i++) {

		if(AbstractDictionary<T>::m_dictionary[i].first == key) { return i; }
	}

	return -1;
}

#endif