datapoints = importdata('result.txt');
for i=1:1:size(datapoints)
    if datapoints(i,3)==1
        plot(datapoints(i,1),datapoints(i,2),'g.');
    elseif datapoints(i,3)==2
        plot(datapoints(i,1),datapoints(i,2),'r.');
    elseif datapoints(i,3)==3
        plot(datapoints(i,1),datapoints(i,2),'m.');  
    else
        disp("Error, Should be only 3 clusters")
    end
    hold on;
end
% datapoints = importdata('3spiral.txt');
% for i=1:size(datapoints)
%    plot(datapoints(i,1),datapoints(i,2),'b.') ;
%    grid on;
%    hold on;
% end