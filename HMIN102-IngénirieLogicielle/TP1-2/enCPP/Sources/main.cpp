#include <iostream>
#include <string>

#include "OrderedDictionary.hpp"
#include "FastDictionary.hpp"
#include "SortedDictionary.hpp"


int main() {

	OrderedDictionary<std::string> orderDico;

	orderDico.put("1", "123");
	orderDico.put("2", "456");
	orderDico.put("3", "789");


	FastDictionary<std::string> fastDico(4);

	fastDico.put("un", "123");
	fastDico.put("deux", "456");
	fastDico.put("trois", "789");
	fastDico.put("quatre", "000");



	SortedDictionary<std::string> sortedDico;

	sortedDico.put("5", "123");
	sortedDico.put("3", "456");
	sortedDico.put("4", "fg");
	sortedDico.put("7", "45fh");
	sortedDico.put("4", "test");
	sortedDico.put("0", "45fxdfg");
	sortedDico.put("4765", "xdfg4");

	return 0;
}