#ifndef ABSTRACTDICTIONARY_HPP
#define ABSTRACTDICTIONARY_HPP

#include <string>
#include <vector>
#include <iostream>

template<typename T>
class AbstractDictionary {

	public:

		AbstractDictionary();

		virtual bool isEmpty() const;
		virtual bool containsKey(const std::string &key) const;

		virtual void put(const std::string &key, const T &object);
		virtual T get(const std::string &key) const;

		virtual void test() const;

	protected:

		virtual unsigned int newIndex(const std::string &key) = 0;
		virtual int findIndex(const std::string &key) const = 0;

		std::vector<std::pair<std::string, T>> m_dictionary;

		unsigned int m_nbElements;
};




template <typename T>
AbstractDictionary<T>::AbstractDictionary(): m_nbElements{0} {}


template <typename T>
bool AbstractDictionary<T>::isEmpty() const {

	if(m_nbElements == 0) { return true; }
	return false;
}

template <typename T>
bool AbstractDictionary<T>::containsKey(const std::string &key) const {

	if(findIndex(key) != -1) { return true; }

	return false;
}

template <typename T>
void AbstractDictionary<T>::put(const std::string &key, const T &object) {

	int searchResult{findIndex(key)};

	if(searchResult == -1) { m_dictionary[newIndex(key)] = std::make_pair(key, object); }
	else { m_dictionary[searchResult].second = object; }
}

template <typename T>
T AbstractDictionary<T>::get(const std::string &key) const {

	int searchResult{findIndex(key)};

	if(searchResult == -1) { return T{}; }

	return m_dictionary[searchResult].second;
}

template<typename T>
void AbstractDictionary<T>::test() const { 

	for(unsigned int i{0}; i < AbstractDictionary<T>::m_dictionary.size(); i++) {

		std::cout << AbstractDictionary<T>::m_dictionary[i].first << ": " << AbstractDictionary<T>::m_dictionary[i].second << std::endl;
	}
}

#endif