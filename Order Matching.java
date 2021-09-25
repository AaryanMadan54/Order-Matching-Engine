import java.util.*;
public class Main 
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		char side;
		String order_ID;
		int quantity;
		int price = 0;
		int action; //To store 1 to submit or 0 to cancel the order
		int order; // To store 1 for limit order, 0 for market order or 2 to cancel & replace order
		List<Integer> cost = new ArrayList<Integer>(); // To store the cost after each order is executed
		LinkedList<String> buy_order = new LinkedList<String> ();
		LinkedList<String> sell_order = new LinkedList<String> ();
		LinkedList<String> lo = new LinkedList<String> ();
		String input = sc.nextLine();
		int ctr = 0;
		int ctr_b = 0;
		int ctr_s = 0;
		while (!(input.equals("END"))) 
		{
			if (ctr != 0) {
				input = sc.nextLine();
			}
			ctr++;

			if (input.equals("END")) 
			{
				break;
			}
			if ((input.substring(0, 3)).equals("SUB")) 
			{
				action = 1;
			} 
			else if((input.substring(0, 3)).equals("CXL"))
			{
				action = 0;
				String id = input.substring(4);
				boolean track = false;
				for(int i = 0 ; i < buy_order.size();i++)
				{
				    int k = buy_order.get(i).indexOf('#');
				    if(buy_order.get(i).substring(k+1).equals(id))
				    {
				        buy_order.remove(i);
				        track = true;
				        break;
				    }
				}
				if(track == false)
				{
				    for(int i = 0; i < sell_order.size(); i++)
				    {
				        int k = sell_order.get(i).indexOf('#');
				        if(sell_order.get(i).substring(k+1).equals(id))
				        {
				            sell_order.remove(i);
				            break;
				        }
				    }
				}
				continue;
			}
			
			if((input.substring(0, 3)).equals("CRP"))//Cancel Or Replace Order
			{
			    action = 2;
			    boolean trck = false;
			    boolean chek = false;
			    String s = "";
			    int o,r;
			        
			    o = input.indexOf(' ', 4);
			    order_ID = input.substring(4, o);
		        r = input.indexOf(' ', o + 1);
		        quantity = Integer.parseInt(input.substring(o+1, r));
		        price = Integer.parseInt(input.substring(r + 1));
		        
		        boolean list_check = false;
		        for(int i = 0; i < lo.size();i++)
		        {
		            if(lo.get(i).equals(order_ID))// To check if the CRP Order is for a List Order or not
		            {
		                list_check = true;
		            }
		        }
		        
		        if(list_check == false)//Do nothing since it is not a List Order
		        {
		            continue;
		        }
			    
			    for(int i = 0; i < buy_order.size(); i++)
			    {
			        int la = buy_order.get(i).indexOf('#');
			        int lb = buy_order.get(i).indexOf('@');
			        int t_p = Integer.parseInt(buy_order.get(i).substring(lb+1,la));
			        int t_q = Integer.parseInt(buy_order.get(i).substring(0, lb));
	                if((buy_order.get(i).substring(la+1)).equals(order_ID))
	                {
	                    if(price == t_p && quantity <= t_q)
	                    {
	                        s = quantity + "@" + price + "#" + order_ID;
	                        buy_order.set(i,s);
	                        chek = true;
	                    }
	                    
	                    else
	                    {
	                        buy_order.remove(i);
	                        for(int j = 0; j < buy_order.size(); j++)
					        {
					            int f = buy_order.get(j).indexOf('#');
					            int x = buy_order.get(j).indexOf('@');
						        int w = Integer.parseInt(buy_order.get(j).substring(x+1, f));
						        s = quantity + "@" + price + "#" + order_ID;
					            if(price > w)
					            {
					                buy_order.add(j,s);
					                chek = true;
					                break;
					            }
					        }
	                    }
					        
					    if(chek == false)
					    {
					        buy_order.add(s);
					    }
					    
	                    trck = true;
	                }
			    }
			    
			    if(trck == false)
			    {
			        chek = false;
			        for(int i = 0; i < sell_order.size();i++)
			        {
			            int ls = sell_order.get(i).indexOf('#');
			            int lp = sell_order.get(i).indexOf('@');
			            int t_p = Integer.parseInt(sell_order.get(i).substring(lp+1,ls));
			            int t_q = Integer.parseInt(sell_order.get(i).substring(0, lp));
	                    if((sell_order.get(i).substring(ls+1)).equals(order_ID))
	                    {
	                        if(price == t_p && quantity <= t_q)
	                        {
	                            s = quantity + "@" + price + "#" + order_ID;
	                            sell_order.set(i,s);
	                            chek = true;
	                        }
	                        
	                        
	                        else
	                        {
	                            sell_order.remove(i);
	                            for(int j = 0; j < sell_order.size(); j++)
					            {
					                int f = sell_order.get(j).indexOf('#');
					                int x = sell_order.get(j).indexOf('@');
						            int w = Integer.parseInt(sell_order.get(j).substring(x+1, f));
						            s = quantity + "@" + price + "#" + order_ID;
					                if(price < w)
					                {
					                    sell_order.add(j,s);
					                    chek = true;
					                    break;
					                }
					            }
	                        }
					        
					       if(chek == false)
					       {
			                   sell_order.add(s);
		     	           }
	                    }
			        }
			    }
			    continue;
			}

			if ((input.substring(4, 6)).equals("LO")) 
			{
				order = 1;
			} 
			else if((input.substring(4, 6)).equals("MO"))
			{
				order = 0;
			}
			else if((input.substring(4, 7)).equals("IOC"))
			{
			    order = 2;
			}
			else//fill or kill
			{
			    order = 3;
			}
            
            if(order == 1 || order == 0)
            {
                side = input.charAt(7);
            }
            
            else
            {
                side = input.charAt(8);
            }
			
			int c, d, e;
			    
			
			if(order == 2 || order == 3)
			{
			    c = input.indexOf(' ', 10);
			    order_ID = input.substring(10,c);
			}
			
			else
			{
			    c = input.indexOf(' ', 9);
			    order_ID = input.substring(9, c);
			}
			
			d = input.indexOf(' ', c + 1);
			if(order == 1 || order == 2 || order == 3)
			{
			    quantity = Integer.parseInt(input.substring(c+1, d));
			}
			else
			{
			    quantity = Integer.parseInt(input.substring(c+1));
			}
			
			if(order == 1)
			{
			    lo.add(order_ID);
			}
			
			if(order == 1 || order == 2 || order == 3)
			{
			    price = Integer.parseInt(input.substring(d + 1));
			}
			
			String temp = "  ";
			if (order == 1) //Limit Order
			{
				if (side == 'B') 
				{
				    boolean chk = false;
					temp = quantity + "@" + price + "#" + order_ID;
					if(ctr_b == 0)
					{
					    buy_order.add(temp);
					    ctr_b++;
					}
					else
					{
					    for(int i = 0; i < buy_order.size();i++)
					    {
					        c = buy_order.get(i).indexOf('#');
					        d = buy_order.get(i).indexOf('@');
						    int a = Integer.parseInt(buy_order.get(i).substring(d+1, c));
					        if(price > a)
					        {
					            buy_order.add(i,temp);
					            chk = true;
					            break;
					        }
					    }
					    if(chk == false)
					    {
					        buy_order.add(temp);
					    }
					}
					
				} 
				else 
				{
				    boolean chk = false;
				    temp = quantity + "@" + price + "#" + order_ID;
				    if(ctr_s == 0)
				    {
				        sell_order.add(temp);
				        ctr_s++;
				    }
				    else
				    {
				        for(int i = 0; i < sell_order.size();i++)
				        {
				            c = sell_order.get(i).indexOf('#');
				            d = sell_order.get(i).indexOf('@');
						    int a = Integer.parseInt(sell_order.get(i).substring(d+1, c));
					        if(price < a)
					        {
					            sell_order.add(i,temp);
					            chk = true;
					            break;
					        }
				        }
				        if(chk == false)
				        {
				            sell_order.add(temp);
				        }
				    }
				}
			} 

			LinkedList<String> buy_order_unsorted = new LinkedList<>(buy_order);
			LinkedList<String> sell_order_unsorted = new LinkedList<>(sell_order);
			if(order == 1) // LIMIT ORDER
			{
			    int sum = 0;//To keep track of the cost when an order is executed
			    int units;
				if(side == 'B')
				{
				    for(int i = 0; i < sell_order.size();i++)
				    {
				        c = sell_order.get(i).indexOf('#');
				        d = sell_order.get(i).indexOf('@');
				        int temp_price = Integer.parseInt(sell_order.get(i).substring(d+1,c));
				        int temp_quantity = Integer.parseInt(sell_order.get(i).substring(0,d));
				        if(price >= temp_price)
				        {
				            if(quantity < temp_quantity)
				            {
				                units = quantity;
				                int z = temp_quantity;
				                z = z - units;
				                int g = sell_order.get(i).indexOf('#');
				                String t = z + "@" + temp_price + "#" + sell_order.get(i).substring(g+1);
				                sell_order.set(i,t);
				                sum = sum + units*temp_price;
				                for(int j = 0; j < buy_order.size();j++)
				                {
				                    int y = buy_order.get(j).indexOf('#');
				                    if((buy_order.get(j).substring(y+1)).equals(order_ID))
				                    {
				                        buy_order.remove(j);
				                    }
				                }
				                break;
				            }
				            else
				            {
				                units = temp_quantity;
				                quantity = quantity - units;
				                
				                String q = quantity + "@" + price + "#" + order_ID;
				                buy_order.set(ctr_b -1,q);
				                
				                sum = sum + (units*temp_price);
				                sell_order.remove(i);
				                i--;
				            }
				        }
				    }
				    cost.add(sum);
				}
				else
				{
				    for(int i = 0; i < buy_order.size();i++)
				    {
				        c = buy_order.get(i).indexOf('#');
				        d = buy_order.get(i).indexOf('@');
				        int temp_price = Integer.parseInt(buy_order.get(i).substring(d+1,c));
				        int temp_quantity = Integer.parseInt(buy_order.get(i).substring(0,d));
				        if(temp_price >= price)
				        {
				            if(quantity < temp_quantity)
				            {
				                units = quantity;
				                int z = temp_quantity;
				                z = z - units;
				                int g = buy_order.get(i).indexOf('#');
				                String t = z + "@" + temp_price + "#" + buy_order.get(i).substring(g+1);
				                buy_order.set(i,t);
				                sum = sum + units*temp_price;
				                for(int j = 0; j < sell_order.size();j++)
				                {
				                    if((sell_order.get(j).substring(c+1)).equals(order_ID))
				                    {
				                        int y = sell_order.get(j).indexOf('#');
				                        sell_order.remove(j);
				                    }
				                }
				                break;
				            }
				            else
				            {
				                units = temp_quantity;
				                quantity = quantity - units;
				                
				                String y = quantity + "@" + price + "#" + order_ID;
				                sell_order.set(ctr_s -1,y);
				                
				                sum = sum + (units*temp_price);
				                buy_order.remove(i);
				                i--;
				            }
				        }
				    }
				    cost.add(sum);
				}
			}
			
			else if(order == 0) //MARKET ORDER
			{
			    int sum = 0;
			    int units;
				if(side == 'B')
				{
				    for(int i = 0; i < sell_order.size();i++)
				    {
				        c = sell_order.get(i).indexOf('#');
				        d = sell_order.get(i).indexOf('@');
				        int temp_price = Integer.parseInt(sell_order.get(i).substring(d+1,c));
				        int temp_quantity = Integer.parseInt(sell_order.get(i).substring(0,d));
				        if(quantity < temp_quantity)
				        {
				            units = quantity;
				            int z = temp_quantity;
				            z = z - units;
			                String t = z + "@" + price + "#" + order_ID;
			                sell_order.set(i,t);
			                sum = sum + units*temp_price;
			                break;
				        }
				        else
				        {
				            units = temp_quantity;
			                quantity = quantity - units;
				                
				            sum = sum + (units*temp_price);
			                sell_order.remove(i);
			                i--;
			            }
				    }
				    cost.add(sum);
				}
				else
				{
				    for(int i = 0; i < buy_order.size(); i++)
				    {
				        c = buy_order.get(i).indexOf('#');
				        d = buy_order.get(i).indexOf('@');
				        int temp_price = Integer.parseInt(buy_order.get(i).substring(d+1,c));
				        int temp_quantity = Integer.parseInt(buy_order.get(i).substring(0,d));
				        if(quantity < temp_quantity)
				        {
				            units = quantity;
				            int z = temp_quantity;
			                z = z - units;
			                String t = z + "@" + price + "#" + order_ID;
			                buy_order.set(i,t);
			                sum = sum + units*temp_price;				                
			                break;
			            }
			            else
			            {
			                units = Integer.parseInt(buy_order.get(i).substring(0,d));
				            quantity = quantity - units;
		                
				            sum = sum + (units*Integer.parseInt(buy_order.get(i).substring(d+1,c)));
				            buy_order.remove(i);
				            i--;
			            }
			        }
			        cost.add(sum);
				}
			}
			
			else if(order == 2)//IOC Order
			{
			    int sum = 0;//To keep track of the cost when an order is executed
			    int units;
				if(side == 'B')
				{
				    c = sell_order.get(0).indexOf('#');
			        d = sell_order.get(0).indexOf('@');
			        int temp_price = Integer.parseInt(sell_order.get(0).substring(d+1,c));
			        int temp_quantity = Integer.parseInt(sell_order.get(0).substring(0,d));
			        if(price >= temp_price)
			        {
			            if(quantity < temp_quantity)
			            {
			                units = quantity;
			                int z = temp_quantity;
			                z = z - units;
			                int g = sell_order.get(0).indexOf('#');
			                String t = z + "@" + temp_price + "#" + sell_order.get(0).substring(g+1);
			                sell_order.set(0,t);
			                sum = sum + units*temp_price;
			            }
				        else
			            {
			                units = temp_quantity;
			                sum = sum + (units*temp_price);
			                sell_order.remove(0);
			            }
			        }
			        cost.add(sum);
				}
			    
			    else
			    {
			        c = buy_order.get(0).indexOf('#');
				    d = buy_order.get(0).indexOf('@');
			        int temp_price = Integer.parseInt(buy_order.get(0).substring(d+1,c));
			        int temp_quantity = Integer.parseInt(buy_order.get(0).substring(0,d));
			        if(temp_price >= price)
			        {
			            if(quantity < temp_quantity)
			            {
			                units = quantity;
			                int z = temp_quantity;
			                z = z - units;
			                int g = buy_order.get(0).indexOf('#');
			                String t = z + "@" + temp_price + "#" + buy_order.get(0).substring(g+1);
			                buy_order.set(0,t);
			                sum = sum + units*temp_price;
			            }
			            else
			            {
			                units = temp_quantity;
			                sum = sum + (units*temp_price);
			                buy_order.remove(0);
			            }
			        }
			        cost.add(sum);
			    }
			}
			
			else//FOK Order
			{
			    int sum = 0;//To keep track of the cost when an order is executed
			    int units;
			    boolean check;
				if(side == 'B')
				{
				    c = sell_order.get(0).indexOf('#');
			        d = sell_order.get(0).indexOf('@');
			        int tmp_price = Integer.parseInt(sell_order.get(0).substring(d+1,c));
			        int tmp_quantity = Integer.parseInt(sell_order.get(0).substring(0,d));
			        if(price >= tmp_price)
			        {
			            if(quantity < tmp_quantity)
			            {
			                units = quantity;
			                int z = tmp_quantity;
			                z = z - units;
			                int g = sell_order.get(0).indexOf('#');
			                String t = z + "@" + tmp_price + "#" + sell_order.get(0).substring(g+1);
			                sell_order.set(0,t);
			                sum = sum + units*tmp_price;
			                break;
			            }
				            
			            else
			            {
			                int t_quant = 0;
			                check = false;
			                int count = 0;
		                    for(int k = 0; k < sell_order.size();k++)
		                    {
		                        c = sell_order.get(k).indexOf('#');
			                    d = sell_order.get(k).indexOf('@');
			                    int tmps_price = Integer.parseInt(sell_order.get(k).substring(d+1,c));
			                    int tmps_quantity = Integer.parseInt(sell_order.get(k).substring(0,d));
	                            t_quant = t_quant + tmps_quantity;
	                            count++;
		                        if(t_quant >= quantity)
		                        {
		                            for(int x = 0; x < count; x++)
		                            {
		                                if(x == count-1)
		                                {
		                                    if(!(price >= tmps_price))
		                                    {
		                                        break;
		                                    }
		                                    units = quantity;
		                                    int z = tmps_quantity;
			                                z = z - units;
			                                int g = sell_order.get(0).indexOf('#');
			                                int hashh = sell_order.get(0).indexOf('#');
			                                int att = sell_order.get(0).indexOf('@');
			                                int pric = Integer.parseInt(sell_order.get(0).substring(att+1,hashh));
			                                String t = z + "@" + pric + "#" + sell_order.get(0).substring(g+1);
			                                sell_order.set(0,t);
			                                sum = sum + (units*pric);
		                                }
		                                else
		                                {
		                                    if(!(price >= tmps_price))
		                                    {
		                                        break;
		                                    }
		                                    int hashtag = sell_order.get(0).indexOf('#');
			                                int at = sell_order.get(0).indexOf('@');
		                                    int quant = Integer.parseInt(sell_order.get(0).substring(0,at));
		                                    int pri = Integer.parseInt(sell_order.get(0).substring(at+1,hashtag));
		                                    sum = sum + (quant*pri);
		                                    sell_order.remove(0);
		                                    quantity = quantity - quant;
		                                }
		                            }
		                            break;
		                        }
		                    }
				        }
				    }
				    cost.add(sum);
				}
				
				else
				{
				    sum = 0;//To keep track of the cost when an order is executed
				    c = buy_order.get(0).indexOf('#');
			        d = buy_order.get(0).indexOf('@');
			        int tmp_price = Integer.parseInt(buy_order.get(0).substring(d+1,c));
			        int tmp_quantity = Integer.parseInt(buy_order.get(0).substring(0,d));
			        if(tmp_price >= price)
			        {
			            if(quantity < tmp_quantity)
			            {
			                units = quantity;
			                int z = tmp_quantity;
			                z = z - units;
			                int g = buy_order.get(0).indexOf('#');
			                String t = z + "@" + tmp_price + "#" + buy_order.get(0).substring(g+1);
			                buy_order.set(0,t);
			                sum = sum + units*tmp_price;
			                break;
			            }
				            
			            else
			            {
			                int t_quant = 0;
			                check = false;
			                int count = 0;
		                    for(int k = 0; k < buy_order.size();k++)
		                    {
		                        c = buy_order.get(k).indexOf('#');
			                    d = buy_order.get(k).indexOf('@');
			                    int tmps_price = Integer.parseInt(buy_order.get(k).substring(d+1,c));
			                    int tmps_quantity = Integer.parseInt(buy_order.get(k).substring(0,d));
	                            t_quant = t_quant + tmps_quantity;
	                            count++;
		                        if(t_quant >= quantity)
		                        {
		                            for(int x = 0; x < count; x++)
		                            {
		                                if(x == count-1)
		                                {
		                                    if(!(tmps_price >= price))
		                                    {
		                                        break;
		                                    }
		                                    units = quantity;
		                                    int z = tmps_quantity;
			                                z = z - units;
			                                int g = buy_order.get(0).indexOf('#');
			                                int hashh = buy_order.get(0).indexOf('#');
			                                int att = buy_order.get(0).indexOf('@');
			                                int pric = Integer.parseInt(buy_order.get(0).substring(att+1,hashh));
			                                String t = z + "@" + pric + "#" + buy_order.get(0).substring(g+1);
			                                buy_order.set(0,t);
			                                sum = sum + (units*pric);
		                                }
		                                else
		                                {
		                                    if(!(tmps_price >= price))
		                                    {
		                                        break;
		                                    }
		                                    int hashtag = buy_order.get(0).indexOf('#');
			                                int at = buy_order.get(0).indexOf('@');
		                                    int quant = Integer.parseInt(buy_order.get(0).substring(0,at));
		                                    int pri = Integer.parseInt(buy_order.get(0).substring(at+1,hashtag));
		                                    sum = sum + (quant*pri);
		                                    buy_order.remove(0);
		                                    quantity = quantity - quant;
		                                }
		                            }
		                            break;
		                        }
		                    }
				        }
				    }
				    cost.add(sum);
				}
			}
		}
		
		
		
		cost.forEach(System.out::println);
		System.out.print("B:");
		for(int i = 0 ; i < buy_order.size();i++)
		{
		    System.out.print(" " + buy_order.get(i));
		}
		System.out.println();
		System.out.print("S:");
		for(int i = 0 ; i < sell_order.size();i++)
		{
		    System.out.print(" " + sell_order.get(i));
		}
	}
}