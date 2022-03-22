package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface ItemExampleRepository extends ReactiveQueryByExampleExecutor<Item>  {

}
