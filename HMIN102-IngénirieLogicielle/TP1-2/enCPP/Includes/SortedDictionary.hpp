#ifndef SORTEDDICTIONARY_HPP
#define SORTEDDICTIONARY_HPP

#include "AbstractDictionary.hpp"

template<typename T>
class SortedDictionary: public AbstractDictionary<T> {

	public:

		SortedDictionary();

	protected:

		unsigned int newIndex(const std::string &key);
		int findIndex(const std::string &key) const;
};

template<typename T>
SortedDictionary<T>::SortedDictionary() {}

// A finir ! 
// + Arranger le tout pour filer solution C++ au prof
// Ne pas oublier le Makefile


template<typename T>
unsigned int SortedDictionary<T>::newIndex(const std::string &key) {

	AbstractDictionary<T>::m_nbElements++;

	if(AbstractDictionary<T>::m_dictionary.empty()) {

		AbstractDictionary<T>::m_dictionary.emplace_back(std::make_pair("", T{}));
		return 0;
	}

	//Cherche la place
	unsigned int currentIndex{static_cast<unsigned int>(AbstractDictionary<T>::m_dictionary.size())};

	while(currentIndex > 0 && AbstractDictionary<T>::m_dictionary[currentIndex-1].first >= key) { currentIndex--; }

	AbstractDictionary<T>::m_dictionary.emplace_back(std::make_pair("", T{}));

	//Décale les éléments
	for(unsigned int i{static_cast<unsigned int>(AbstractDictionary<T>::m_dictionary.size())-1}; i > currentIndex; i--) { 

		AbstractDictionary<T>::m_dictionary[i] = AbstractDictionary<T>::m_dictionary[i-1];
	}

	return currentIndex;
}

template<typename T>
int SortedDictionary<T>::findIndex(const std::string &key) const {

	//Recherche dichotomique

	if(AbstractDictionary<T>::m_dictionary.size() == 0) { return -1; }

	int begin{0}, end{static_cast<int>(AbstractDictionary<T>::m_dictionary.size()-1)}, currentIndex{0};

	while(begin <= end) {

		currentIndex = (begin + end)/2;
	
		if(AbstractDictionary<T>::m_dictionary[currentIndex].first == key) { return currentIndex; }
		if(AbstractDictionary<T>::m_dictionary[currentIndex].first < key) { begin = currentIndex+1; }
		if(AbstractDictionary<T>::m_dictionary[currentIndex].first > key) { end = currentIndex-1; }
	}

	return -1;
}

#endif